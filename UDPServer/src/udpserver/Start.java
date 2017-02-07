/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import javax.swing.UIManager;

/**
 *
 * @author gkovacs02
 */
public class Start {

    private static Tools tools;

    public static void start() throws Exception {
        String separator = System.getProperty("file.separator");
        String path = System.getProperty("user.dir") + separator + "xml" + separator;
        /*
         * debug a fájlba ír
         * 
         */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }


        try {
            String defaultDir = System.getProperty("user.dir")
                    + separator
                    + "diag"
                    + separator
                    + "debug";

            File dir = new File(defaultDir);
            dir.mkdir();

            String defaultErrorFile = defaultDir
                    + separator
                    + System.currentTimeMillis()
                    + ".log";

            OutputStream output = new FileOutputStream(defaultErrorFile);
            PrintStream printOut = new PrintStream(output);
            System.setOut(printOut);
        } catch (Exception Ex) {
            System.err.println(Ex);
        }
        /*
         * 
         * 
         */
        //System.out.println("path: " + path);
        System.out.println("---------------------------UDP_Szerver---------------------------");
        System.out.println("");
        System.out.println("\tServer start\t:\t" + Tools.actualDate("yyyy_MM_dd HH:mm:ss:SS").toString());
        System.out.println("");
        System.out.println("\tProcess\t\t:\t" + ManagementFactory.getRuntimeMXBean().getName());
        System.out.println("\tJava -version\t:\t" + System.getProperty("java.version"));
        System.out.println("-----------------------------------------------------------------");
        System.out.println("");
        tools = new Tools();
        UDPServer[] us = null;
        ArrayList files = new ArrayList();
        try {
            File inFolder = new File(path);
            if (inFolder.isDirectory()) {
                String filesString[] = inFolder.list();
                for (int i = 0; i < filesString.length; i++) {
                    //System.out.println(filesString[i]);
                    String fileName[] = filesString[i].split("\\.");
                    if (fileName.length > 0) {
                        if (fileName[fileName.length - 1].equals("xml")) {
                            files.add(filesString[i]);
                            //System.out.println(filesString[i]);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        if (!files.isEmpty()) {
            us = new UDPServer[files.size()];
            for (int i = 0; i < files.size(); i++) {
                us[i] = new UDPServer(path + files.get(i).toString());
            }
        }
        Tray.tray(us);
    }
}
