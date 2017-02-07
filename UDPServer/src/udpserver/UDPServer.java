/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

/**
 *
 * @author gabeszakem
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.*;
import java.util.*;
import logreader.Colorize;
import logreader.MyTableCellRender;
import java.util.Date;

public final class UDPServer {

    JFrame frame;
    JPanel mainPanel;
    JButton buttonStart, buttonStop, buttonCurrent, buttonBrowseBack;
    JScrollPane paneCurrentView, paneArea, paneBrowseBack;
    DatagramSocket socket;
    Thread thread;
    int port, maxRow;
    JTable tableCurrentView, tableEvent, tableBrowseBack;
    DefaultTableModel modelCurrentView, modelEvent, modelBrowseBack;
    XML xml;
    ArrayList puffer = new ArrayList();
    String xmlString, title, defaultErrorFile, defaultLogFile, defaultErrorFileExt;
    boolean debug = true;
    boolean run = false;
    boolean bbTooltipText = false;
    boolean cwTooltipText = false;
    boolean enableColor = true;
    int countError = 200;
    int[] widthColumn = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    int frameSize = 20;
    int paneAreaHeight = 60;
    int buttonHeight = 40;
    int buttonDistance = 10;
    int numberOfButtons = 4;
    Colorize c;

    public static void main(String[] args) throws Exception {
        try {
            /* try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            
            } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            }*/
            Start.start();

        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }

    public UDPServer(String path) throws Exception {

        System.out.println(Tools.actualDate("yyyy_MM_dd HH:mm:ss:SS").toString() + "\tpath:\t" + path);
        xml = new XML();
        try {
            xml.xml(path);
        } catch (Exception Ex) {
            System.err.println(Ex);
        }

        String debugString = xml.getDebug();
        debugString = debugString.toUpperCase();
        if (debugString.toUpperCase().equals("TRUE")) {
            debug = true;
        } else {
            debug = false;
        }
        bbTooltipText = xml.isBrowseBackToolTipText();
        cwTooltipText = xml.isCurrentViewTooltipText();
        //System.out.println("debug:"+debug);
        String separator = System.getProperty("file.separator");
        defaultErrorFile = System.getProperty("user.dir")
                + separator + "diag"
                + separator + xml.getPort()
                + separator;
        defaultErrorFileExt = "log";
        defaultLogFile = xml.getDefaultLogFile();
        title = xml.getTitle();
        int ONE_DAY = 1000 * 60 * 60 * 24;/* (ms)*(s)*(m)*(h) */
        int HALF_DAY = ONE_DAY / 2;
        int HOUR = HALF_DAY / 12;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                //System.out.println("window closed");
                frame.setState(JFrame.ICONIFIED);
                frame.setVisible(false);
            }
        });
        frame.setUndecorated(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        String[] columnNamesEvent = {"Dátum", "Idő", "Esemény"};
        Object[][] dataEvent = {};
        modelEvent = new DefaultTableModel(dataEvent, columnNamesEvent);

        tableEvent = new JTable(modelEvent);
        tableEvent.setAutoscrolls(true);
        tableEvent.setEnabled(false);
        tableEvent.setForeground(Color.red);
        tableEvent.setBackground(Color.white);
        tableEvent.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableEvent.getTableHeader().setReorderingAllowed(false);

        int[] evenWidthColumn = {70, 90, 640};
        for (int i = 0; i < tableEvent.getColumnCount(); i++) {
            TableColumn eventColumn = tableEvent.getColumnModel().getColumn(i);
            eventColumn.setPreferredWidth(evenWidthColumn[i]);
        }
        paneArea = new JScrollPane(tableEvent);

        buttonStart = new JButton("Start");
        buttonStart.setBackground(Color.GREEN);
        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                modelEvent = newLine(modelEvent, "Start button pressed", debug);
                if (run != true) {
                    try {
                        StartThread startThread = new StartThread();
                    } catch (Exception ex) {
                        System.err.println(ex);
                        modelEvent = newLine(modelEvent, ex.toString(), debug);
                    }
                }
            }
        });

        buttonStop = new JButton("Stop");
        buttonStop.setBackground(Color.red);
        buttonStop.addActionListener(new ActionListener() {

            @SuppressWarnings("static-access")
            @Override
            public void actionPerformed(ActionEvent ae) {

                int optRet = JOptionPane.showConfirmDialog(null, "Biztos leállítod a lekérdezést?", "Kérdés", JOptionPane.YES_NO_OPTION);

                if (optRet == JOptionPane.YES_OPTION) {
                    modelEvent = newLine(modelEvent, "Stop button pressed", debug);
                    thread.interrupted();
                    run = false;
                    try {
                        socket.close();
                        modelEvent = newLine(modelEvent, "Socket closed on " + port + " port", debug);
                    } catch (Exception e) {
                        //System.err.println("Socket close exeptcion: " + e);
                        modelEvent = newLine(modelEvent, e.toString(), debug);
                    }
                    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setTitle(title);
//                    trayIcon.setImage(imageStop);
                }

                /* mb.dispose();*/
            }
        });

        buttonCurrent = new JButton("Current");
        buttonCurrent.setBackground(Color.LIGHT_GRAY);
        buttonCurrent.setEnabled(false);
        buttonCurrent.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                modelEvent = newLine(modelEvent, "Current button pressed", debug);

                tableCurrentView.setVisible(true);
                paneCurrentView.setVisible(true);
                tableBrowseBack.setVisible(false);
                paneBrowseBack.setVisible(false);
                buttonCurrent.setEnabled(false);
                buttonBrowseBack.setEnabled(true);

            }
        });

        buttonBrowseBack = new JButton("Browse Back");
        buttonBrowseBack.setBackground(Color.LIGHT_GRAY);
        buttonBrowseBack.setEnabled(true);
        buttonBrowseBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                modelEvent = newLine(modelEvent, "Browse back button pressed", debug);
                Vector dataVector = new Vector();
                dataVector = (Vector) modelCurrentView.getDataVector().clone();
                Vector col = new Vector(modelCurrentView.getColumnCount());
                for (int i = 0; i < modelCurrentView.getColumnCount(); i++) {
                    col.add(i, modelCurrentView.getColumnName(i));
                }
                modelBrowseBack.setDataVector(dataVector, col);
                for (int i = 0; i < tableBrowseBack.getColumnCount(); i++) {
                    TableColumn columnBrowseBack = tableBrowseBack.getColumnModel().getColumn(i);
                    columnBrowseBack.setPreferredWidth(widthColumn[i]);
                }
                tableCurrentView.setVisible(false);
                paneCurrentView.setVisible(false);
                tableBrowseBack.setVisible(true);
                paneBrowseBack.setVisible(true);
                buttonCurrent.setEnabled(true);
                buttonBrowseBack.setEnabled(false);
                if (enableColor) {
                    tableBrowseBack.removeColumn(tableBrowseBack.getColumn("bground"));
                    tableBrowseBack.removeColumn(tableBrowseBack.getColumn("fground"));
                    tableBrowseBack.removeColumn(tableBrowseBack.getColumn("tooltipcolumn"));
                    tableBrowseBack.removeColumn(tableBrowseBack.getColumn("tooltiptext"));

                }
            }
        });
        String[] columnNames;
        if (enableColor) {
            columnNames = new String[]{
                        xml.getHeader_01(),
                        xml.getHeader_02(),
                        xml.getHeader_03(),
                        xml.getHeader_04(),
                        xml.getHeader_05(),
                        xml.getHeader_06(),
                        xml.getHeader_07(),
                        xml.getHeader_08(),
                        xml.getHeader_09(),
                        xml.getHeader_10(),
                        xml.getHeader_11(),
                        xml.getHeader_12(),
                        "bground",
                        "fground",
                        "tooltipcolumn",
                        "tooltiptext"};
        } else {
            columnNames = new String[]{
                        xml.getHeader_01(),
                        xml.getHeader_02(),
                        xml.getHeader_03(),
                        xml.getHeader_04(),
                        xml.getHeader_05(),
                        xml.getHeader_06(),
                        xml.getHeader_07(),
                        xml.getHeader_08(),
                        xml.getHeader_09(),
                        xml.getHeader_10(),
                        xml.getHeader_11(),
                        xml.getHeader_12(),};
        }
        Object[][] data = {};

        modelCurrentView = new DefaultTableModel(data, columnNames);
        Vector vColumnNames = new Vector();
        for (int i = 0; i < columnNames.length; i++) {
            vColumnNames.add(columnNames[i].toString());
            System.out.println("Header" + i + ": " + columnNames[i].toString());
        }
        tableCurrentView = new JTable(modelCurrentView);
        tableCurrentView.setAutoscrolls(true);
        tableCurrentView.setEnabled(false);
        tableCurrentView.setForeground(Color.BLUE);
        tableCurrentView.setBackground(Color.WHITE);
        tableCurrentView.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableCurrentView.getTableHeader().setReorderingAllowed(false);
        if (enableColor) {
            tableCurrentView.removeColumn(tableCurrentView.getColumn("bground"));
            tableCurrentView.removeColumn(tableCurrentView.getColumn("fground"));
            tableCurrentView.removeColumn(tableCurrentView.getColumn("tooltipcolumn"));
            tableCurrentView.removeColumn(tableCurrentView.getColumn("tooltiptext"));
        }
        /*MyTableCellRender tableCurrentViewCellRender = new MyTableCellRender();
        tableCurrentViewCellRender.setBackgoundColor(tableCurrentView.getBackground());
        tableCurrentViewCellRender.setForegoundColor(tableCurrentView.getForeground());
        tableCurrentViewCellRender.setSelectionBackGround(tableCurrentView.getSelectionBackground());
        tableCurrentViewCellRender.setSelectionForeGround(tableCurrentView.getSelectionForeground());
        tableCurrentViewCellRender.setEnableToolTipText(cwTooltipText);
        tableCurrentViewCellRender.setColumnNamesArray(vColumnNames);
        tableCurrentView.setDefaultRenderer(Object.class, tableCurrentViewCellRender);*/

        modelBrowseBack = new DefaultTableModel(data, columnNames);
        tableBrowseBack = new JTable(modelBrowseBack);
        tableBrowseBack.setAutoscrolls(true);
        tableBrowseBack.setEnabled(false);
        tableBrowseBack.setForeground(Color.BLUE);
        tableBrowseBack.setBackground(Color.LIGHT_GRAY);
        tableBrowseBack.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableBrowseBack.getTableHeader().setReorderingAllowed(false);


        /*MyTableCellRender TableBrowseBackCellRender = new MyTableCellRender();
        TableBrowseBackCellRender.setBackgoundColor(tableBrowseBack.getBackground());
        TableBrowseBackCellRender.setForegoundColor(tableBrowseBack.getForeground());
        TableBrowseBackCellRender.setSelectionBackGround(tableBrowseBack.getSelectionBackground());
        TableBrowseBackCellRender.setSelectionForeGround(tableBrowseBack.getSelectionForeground());
        TableBrowseBackCellRender.setEnableToolTipText(bbTooltipText);
        TableBrowseBackCellRender.setColumnNamesArray(vColumnNames);
        tableBrowseBack.setDefaultRenderer(Object.class, TableBrowseBackCellRender);*/
        String[] widthColumnString = xml.getWidth_column();
        if (enableColor) {
            String[] tempWidthColumnString = new String[widthColumnString.length + 4];
            for (int i = 0; i < tempWidthColumnString.length; i++) {
                if (i < widthColumnString.length) {
                    tempWidthColumnString[i] = widthColumnString[i];
                } else {
                    tempWidthColumnString[i] = "0";
                }
            }
            widthColumnString = new String[tempWidthColumnString.length];
            widthColumnString = tempWidthColumnString;
        } else {
        }
        if (enableColor) {
            int[] tempWidthColumn = new int[widthColumn.length + 4];
            for (int i = 0; i < tempWidthColumn.length; i++) {
                if (i < widthColumn.length) {
                    tempWidthColumn[i] = widthColumn[i];
                } else {
                    tempWidthColumn[i] = 0;
                }
            }
            widthColumn = new int[tempWidthColumn.length];
            widthColumn = tempWidthColumn;
        }
        for (int i = 0; i < tableCurrentView.getColumnCount(); i++) {
            TableColumn column = tableCurrentView.getColumnModel().getColumn(i);

            try {
                widthColumn[i] = Integer.parseInt(widthColumnString[i]);
            } catch (Exception ex) {
                StackTraceElement[] STE = ex.getStackTrace();
                for (int ind = STE.length - 1; ind >= 0; ind--) {
                    if (STE[i].getClassName().equals(this.getClass().getName())) {
                        System.out.println(ex + "\t\tclass: " + STE[ind].getClassName() + "\t\tline: " + STE[ind].getLineNumber());
                    }
                }
                modelEvent = newLine(modelEvent, ex.toString(), debug);
            }
            column.setPreferredWidth(widthColumn[i]);
        }

        if (debug == false) {
            paneAreaHeight = 0;
        }

        paneCurrentView = new JScrollPane(tableCurrentView);

        tableBrowseBack.setDefaultRenderer(Object.class, new MyTableCellRender());
        paneBrowseBack = new JScrollPane(tableBrowseBack);
        mainPanel.add(buttonCurrent);
        mainPanel.add(buttonBrowseBack);
        mainPanel.add(buttonStart);
        mainPanel.add(buttonStop);
        mainPanel.add(paneCurrentView);
        mainPanel.add(paneBrowseBack);
        mainPanel.add(paneArea);

        tableCurrentView.setVisible(true);
        paneCurrentView.setVisible(true);
        tableBrowseBack.setVisible(false);
        paneBrowseBack.setVisible(false);

        frame.add(mainPanel);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        frame.setPreferredSize(new Dimension(800, 600));
        int frameWidth = screenSize.width;
        int frameHeight = screenSize.height - 30;
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.setMinimumSize(new Dimension(800, 600));
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                resize();
                tableCurrentView.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                tableBrowseBack.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                tableEvent.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            }
        });
        resize();
        frame.setVisible(false);
        //Image imageFrame = Toolkit.getDefaultToolkit().getImage("images/log-icon16.png");
        Image imageFrame = Toolkit.getDefaultToolkit().getImage(PasswordWindow.class.getResource("images/log-icon16.png"));
        frame.setIconImage(imageFrame);
        try {
            StartThread startThread = new StartThread();
        } catch (Exception ex) {
            System.err.println(ex);
            modelEvent = newLine(modelEvent, ex.toString(), debug);
        }
        paneArea.setVisible(debug);
        if (enableColor) {
            c = new Colorize();
            tableCurrentView.setDefaultRenderer(Object.class, new MyTableCellRender());
        }
        if (enableColor) {
            javax.swing.Timer timerRefreshColour = new javax.swing.Timer(300000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    try {
                        if (frame.isVisible()) {
                            c = new Colorize();
                            System.out.println("new color :" + port + " " + new Date());
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                }
            });
            timerRefreshColour.setRepeats(true);
            timerRefreshColour.start();
        }
        javax.swing.Timer timer = new javax.swing.Timer(HOUR, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Compress.compress(defaultLogFile);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    public class StartThread implements Runnable {

        @SuppressWarnings({"CallToThreadStartDuringObjectConstruction", "static-access"})
        StartThread() throws Exception {
            try {
                String portString = xml.getPort();
                port = Integer.parseInt(portString);

            } catch (NumberFormatException exp) {
                port = 8080;
                System.err.println("port" + exp);
                modelEvent = newLine(modelEvent, exp.toString(), debug);
            }

            try {
                String maxRowString = xml.getMaxRow();
                maxRow = Integer.parseInt(maxRowString);
            } catch (NumberFormatException exp) {
                maxRow = 500;
                //System.err.println("maxrow" + exp);
                modelEvent = newLine(modelEvent, exp.toString(), debug);
            }

            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[1024];
                try {
                    socket = new DatagramSocket(port);
                    socket.setReuseAddress(true);
                    run = true;
                    //System.out.println("Socket created");
                    modelEvent = newLine(modelEvent, "Socket created on " + port + " port", debug);
                    while (true) {
                        try {
                            Arrays.fill(buffer, (byte) 0);
                            frame.setTitle(title + " is running on " + port + " port");
//                            trayIcon.setImage(image);
                            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                            //Receive request from client
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                            socket.receive(packet);

                            int count = 1;
                            for (; buffer[count] != 0 & count < buffer.length - 1; count++) {
                            }


                            InetAddress client = packet.getAddress();
                            int clientPort = packet.getPort();

                            String message = Tools.convert(buffer);
                            message = message.substring(0, count);
                            if (debug == true) {
                                //System.out.println("üzenet: " + message);
                                String result = "";
                                for (int i = 0; i < count; i++) {
                                    result += Integer.toString(i) + ":"
                                            + Integer.toString((buffer[i] & 0xff) + 0x100, 16).substring(1)
                                            + " ";
                                }
                                //System.out.println("buffer: " + result);
                                if (0 < countError) {
                                    LogFile.write(defaultErrorFile, result, defaultErrorFileExt);
                                    LogFile.write(defaultErrorFile, "--------------------------------------------", defaultErrorFileExt);
                                    LogFile.write(defaultErrorFile, message, defaultErrorFileExt);
                                    LogFile.write(defaultErrorFile, "--------------------------------------------", defaultErrorFileExt);
                                    countError--;
                                }

                            }
                            String[] ss = {"", "", "", "", "", "", "", "", "", "", "", ""};
                            String[] begin = xml.getBegin();
                            String[] end = xml.getEnd();
                            for (int i = 0, pointer = 0; i < 12; i++) {

                                Split spl = new Split();
                                try {
                                    spl.begin = begin[i];
                                } catch (Exception ex) {
                                    spl.begin = "";
                                }
                                try {
                                    spl.end = end[i];
                                } catch (Exception ex) {
                                    spl.end = "";
                                }
                                SplitInt spi = new SplitInt();

                                try {
                                    spi.begInt = Integer.parseInt(spl.begin);
                                } catch (Exception ex) {
                                    spi.begInt = pointer;
                                }
                                boolean ok = false;
                                try {
                                    spi.endInt = Integer.parseInt(spl.end);
                                    ok = true;
                                } catch (Exception ex) {
                                    if (spl.end.toUpperCase().equals("CTRLBBBB")) {
                                        if (spi.begInt < count) {
                                            for (int idx = spi.begInt; (idx < count) & (ok == false); idx++) {
                                                if (Integer.toString((buffer[idx] & 0xff) + 0x100, 16).substring(1).equals("0d")) {
                                                    spi.endInt = idx;
                                                    ok = true;
                                                }
                                            }
                                        }
                                    } else {
                                        String character = Tools.toHex(spl.end);
                                        if (spi.begInt < count) {
                                            boolean chk = false;
                                            for (int idx = spi.begInt; (idx < count) & (ok == false); idx++) {
                                                if (Integer.toString((buffer[idx] & 0xff) + 0x100, 16).substring(1).equals(character)) {
                                                    spi.endInt = idx;
                                                    ok = true;
                                                    /*Az 1081 -es portra az üzenetbe @2%5d@ karakter sorozat érkezik, mivel @-nál vágjuk a sztringet
                                                     * ezért ha a kukac után 2-vel % karakterünk van vagy a @  elött 5d akkor nem daraboljuk a stringet
                                                     */
                                                    if (Integer.toString((buffer[idx + 2] & 0xff) + 0x100, 16).substring(1).equals("25")) {
                                                        ok = false;
                                                        chk = true;
                                                    }
                                                    if (chk == true) {
                                                        if (Integer.toString((buffer[idx - 1] & 0xff) + 0x100, 16).substring(1).equals("64")) {
                                                            if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("35")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("30")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("31")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("32")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("33")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("34")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("35")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("36")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("37")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("38")) {
                                                                ok = false;
                                                            } else if (Integer.toString((buffer[idx - 2] & 0xff) + 0x100, 16).substring(1).equals("39")) {
                                                                ok = false;
                                                            }
                                                        }
                                                    }
                                                } else if (idx == count - 1) {
                                                    spi.endInt = idx + 1;
                                                    ok = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (ok == true) {
                                    ss[i] = message.substring(spi.begInt, spi.endInt).replaceAll("\\s+", " ").replaceAll("\\;", "");
                                    pointer = spi.endInt + 1;
                                } else {
                                    ss[i] = "";
                                }
                                /*System.out.println("idx:" + i + " begin: "
                                + spl.begin + " end: " + spl.end + " begint: "
                                + spi.begInt + " endint: " + spi.endInt
                                + " pointer: " + pointer);*/
                            }
                            String sd = "Substrings: ";
                            for (int i = 0; i < ss.length; i++) {
                                sd = sd + i + ":" + ss[i] + " ";
                            }
                            //System.out.println(sd);

                            String date;
                            String myDate;

                            try {
                                myDate = xml.getDate().toUpperCase();
                            } catch (Exception ex) {
                                myDate = "FALSE";
                                System.err.println(ex);
                                modelEvent = newLine(modelEvent, ex.toString(), debug);
                            }
                            try {
                                /*az setup.xml fájlból lehet kiválasztani, hogy a rendszeridőt vagy az üzenetben megkapott
                                 * időt használja a program alapértelmezetként.
                                 */
                                if (myDate.equals("FALSE")) {
                                    date = ss[0];
                                    //System.out.println("date:" + date);
                                    try {
                                        String[] dateArray;
                                        dateArray = date.split("\\.");
                                        //System.out.println("length:" + dateArray.length);
                                        if (dateArray.length == 3) {
                                            String timeFromMessage = "";
                                            if (dateArray[2].length() == 2) {
                                                timeFromMessage = "20" + dateArray[2] + "." + dateArray[1] + "." + dateArray[0] + " " + ss[1];

                                            } else if (dateArray[2].length() == 4) {
                                                timeFromMessage = dateArray[2] + "." + dateArray[1] + "." + dateArray[0] + " " + ss[1];
                                            } else {//Az évet vagy 2 vagy 4 karakterrel írjuk, ha ez nem teljesűl akkor új kivételt dobunk.
                                                throw (new Exception("Dataformat error"));
                                            }

                                            /*A plc nem tudja kezelni a nyári -téli időszámítást. A programrész összehasonlítja a kapott időt a
                                             * rendszeridővel és ha az eltérés 50 és 70 perc között van akkor az üzenetben kapott időt megnövelem
                                            1 órávval
                                            ----------------------------------------------------------------------------------------------------*/
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SS");
                                            Date dateFromMessage = formatter.parse(timeFromMessage);
                                            Date systemDate = new Date();                                             //rendszeridő
                                            long timeDeviation = systemDate.getTime() - dateFromMessage.getTime();      //eltérés a két idő között
                                            //long timeDeviationAbs = Math.abs(timeDeviation);                          // A biztonság kedvéért abszolut értékben
                                            long newTimemSec = 0L;
                                            if ((timeDeviation > 3000000L) & (timeDeviation < 4200000L)) {        //Annak ellenőrzése hogy kell-e korigálni
                                                newTimemSec = dateFromMessage.getTime() + 3600000L;                    //+1 Óra msec-ben
                                            } else {
                                                newTimemSec = dateFromMessage.getTime();                               //Nincs korrekció
                                            }
                                            Date newDateTime = new Date(newTimemSec);                                  //Visszaalakítás Date formában
                                            SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy.MM.dd");       // A dátum formázása
                                            SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss:SS");      // Az idő formázása

                                            ss[0] = formatterDate.format(newDateTime);
                                            ss[1] = formatterTime.format(newDateTime);
                                            /*----------------------------------------------------------------------------------------------------*/
                                        }
                                    } catch (Exception ex) {
                                        //System.err.println("580" + ex);
                                        //System.out.println("date is the system date");
                                        ss[0] = Tools.actualDate("yyyy.MM.dd");
                                        ss[1] = Tools.actualDate("HH:mm:ss:SS");
                                        modelEvent = newLine(modelEvent, ex.toString(), debug);
                                        modelEvent = newLine(modelEvent, "date is the system date", debug);
                                    }
                                } else {
                                    //System.out.println("date is the system date");
                                    ss[0] = Tools.actualDate("yyyy.MM.dd");
                                    ss[1] = Tools.actualDate("HH:mm:ss:SS");
                                }
                                if (enableColor) {
                                    Vector v = new Vector();
                                    v.addAll(Arrays.asList(ss));
                                    //c.setEnableToolTipText(false);
                                    v = c.calculateColors(v);
                                    if (v.size() == ss.length + 4) {
                                        modelCurrentView.insertRow(0, new Object[]{v.get(0), v.get(1), v.get(2), v.get(3), v.get(4), v.get(5),
                                                    v.get(6), v.get(7), v.get(8), v.get(9), v.get(10), v.get(11), v.get(12), v.get(13), v.get(14), v.get(15)});
                                    }

                                } else {
                                    modelCurrentView.insertRow(0, new Object[]{ss[0], ss[1], ss[2], ss[3], ss[4], ss[5], ss[6], ss[7], ss[8], ss[9], ss[10], ss[11]});
                                }
                                int rowCount = modelCurrentView.getRowCount();
                                if (rowCount >= maxRow) {
                                    modelCurrentView.removeRow(rowCount - 1);
                                }

                                String log = "";
                                for (int i = 0; i < ss.length; i++) {
                                    log = log + ss[i] + ";";
                                }
                                log = log + ";" + client.toString() + ";" + clientPort;
                                //System.out.println("defaultLogFile: " + defaultLogFile);
                                try {
                                    if (!puffer.isEmpty()) {
                                        for (int i = 0; i < puffer.size(); i++) {
                                            LogFile.write(defaultLogFile, puffer.get(i).toString());
                                        }
                                        puffer.clear();
                                        modelEvent = newLine(modelEvent, "data is replaced", debug);
                                    }
                                    LogFile.write(defaultLogFile, log);
                                } catch (Exception ex) {
                                    puffer.add(log);
                                    StackTraceElement[] STE = ex.getStackTrace();
                                    for (int i = STE.length - 1; i >= 0; i--) {
                                        if (STE[i].getClassName().equals(this.getClass().getName())) {
                                            System.out.println(ex + "\t\tclass: " + STE[i].getClassName() + "\t\tline: " + STE[i].getLineNumber());
                                        }
                                    }
                                    modelEvent = newLine(modelEvent, ex.toString(), debug);
                                }
                            } catch (Exception ex) {
                                StackTraceElement[] STE = ex.getStackTrace();
                                for (int i = STE.length - 1; i >= 0; i--) {
                                    if (STE[i].getClassName().equals(this.getClass().getName())) {
                                        System.out.println(ex + "\t\tclass: " + STE[i].getClassName() + "\t\tline: " + STE[i].getLineNumber());
                                    }
                                }
                                modelEvent = newLine(modelEvent, ex.toString(), debug);
                            }
                        } catch (UnknownHostException ue) {
                            System.err.println(ue);
                            modelEvent = newLine(modelEvent, ue.toString(), debug);
                        }
                    }
                } catch (java.net.BindException b) {
                    System.err.println(b);
                    frame.setTitle(title + " ERROR :" + b);
                    modelEvent = newLine(modelEvent, b.toString(), debug);
                }
            } catch (IOException e) {
                System.err.println(e);
                modelEvent = newLine(modelEvent, e.toString(), debug);
            }
        }
    }

    void resize() {
        int halfFrameSize = frameSize / 2;
        int paneX = halfFrameSize;
        int paneY = paneAreaHeight + buttonHeight + frameSize;
        int paneWidth = frame.getWidth() - ((frameSize) + paneX);
        int paneHeight = frame.getHeight() - ((frameSize * 2) + paneY);
        paneCurrentView.setBounds(paneX, paneY, paneWidth, paneHeight);
        paneBrowseBack.setBounds(paneX, paneY, paneWidth, paneHeight);
        int paneAreaX = paneX;
        int paneAreaY = 0;
        int paneAreaWidth = paneCurrentView.getWidth();
        paneArea.setBounds(paneAreaX, paneAreaY, paneAreaWidth, paneAreaHeight);
        int buttonWidth = (paneWidth - (buttonDistance * (numberOfButtons - 1))) / numberOfButtons;
        int buttonY = paneAreaHeight + halfFrameSize;
        int buttonCurrentX = paneWidth - ((buttonWidth * numberOfButtons) + (buttonDistance * (numberOfButtons - 1))) + halfFrameSize;
        int buttonBrowseBackX = paneWidth - ((buttonWidth * (numberOfButtons - 1)) + (buttonDistance * (numberOfButtons - 2))) + halfFrameSize;
        int buttonStartX = paneWidth - ((buttonWidth * (numberOfButtons - 2)) + (buttonDistance * (numberOfButtons - 3))) + halfFrameSize;
        int buttonStopX = paneWidth - ((buttonWidth * (numberOfButtons - 3)) + (buttonDistance * (numberOfButtons - numberOfButtons))) + halfFrameSize;
        buttonStart.setBounds(buttonStartX, buttonY, buttonWidth, buttonHeight);
        buttonStop.setBounds(buttonStopX, buttonY, buttonWidth, buttonHeight);
        buttonCurrent.setBounds(buttonCurrentX, buttonY, buttonWidth, buttonHeight);
        buttonBrowseBack.setBounds(buttonBrowseBackX, buttonY, buttonWidth, buttonHeight);
    }

    static DefaultTableModel newLine(DefaultTableModel m, String text, boolean debug) {
        m.insertRow(0, new Object[]{Tools.actualDate("yyyy.MM.dd"), Tools.actualDate("HH:mm:ss:SS"), text});
        System.out.println(Tools.actualDate("yyyy.MM.dd") + " " + Tools.actualDate("HH:mm:ss:SS") + " " + text);
        if (debug == true) {
            int eventRowCount = m.getRowCount();
            int maxEvent = 100;
            while (eventRowCount > maxEvent) {
                if (eventRowCount > maxEvent) {
                    m.removeRow(eventRowCount - 1);
                }
                eventRowCount--;
            }
        }
        return m;
    }

    public class Split {

        public String begin;
        public String end;
    }

    public class SplitInt {

        public int begInt;
        public int endInt;
    }
}
