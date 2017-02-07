/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import logreader.xml.WriteXMLFile;
import logreader.xml.ReadXMLFile;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.management.ManagementFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/**
 *
 * @author gabesz
 */
public class Logreader {

    private static LogFrame logFrame = null;
    private static String currentDirectory;
    private static String path;

    public static void main(String[] args) {
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
        String separator = System.getProperty("file.separator");
        String pathDir=System.getProperty("user.dir") + separator + "logreaderxml";
        path = pathDir + separator + "setup.xml";
        //DriveFaultWritter d=new DriveFaultWritter();
        File dir=new File(pathDir);
        dir.mkdirs();
        ReadXMLFile x = new ReadXMLFile(path,"setup","filepath");
        if (x.getValue() == null) {
            currentDirectory = "D:\\javaprog";
        } else {
            currentDirectory = x.getValue();
        }
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.XPStyle");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        try {
            UIManager.put("FileChooser.readOnly", Boolean.TRUE);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        logFrame();

        final javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                logFrameUpdate();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private static void logFrame() {
        if (logFrame != null) {
            if (logFrame.isVisible()) {
                clear();
            }
        } else {
            try {
                logFrame = new LogFrame(currentDirectory);
                logFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                logFrame.updateTree();
                logFrame.setMaxSize();
                logFrame.setVisible(true);
                try {
                    WriteXMLFile xml = new WriteXMLFile(currentDirectory,path);
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            } catch (java.lang.NullPointerException Ex) {
                Ex.printStackTrace();
                System.out.println("wrong path");
                FolderSelector fs = new FolderSelector();
                disableNewFolderButton(fs);
                int result = fs.jFileChooser1.showOpenDialog(null);
                switch (result) {
                    case JFileChooser.APPROVE_OPTION:
                        System.out.println("Approve (Open) was clicked");
                        currentDirectory = fs.jFileChooser1.getSelectedFile().toString();
                        fs.setVisible(false);
                        fs = null;
                        logFrame();
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        System.out.println("Cancel or the close-dialog icon was clicked");
                        fs.setVisible(false);
                        fs = null;
                        System.exit(0);
                        break;
                    case JFileChooser.ERROR_OPTION:
                        System.out.println("Error");
                        System.exit(0);
                        break;
                }
            }
        }
    }

    private static void logFrameUpdate() {
        if (logFrame != null) {
            if (logFrame.isVisible()) {
                if (logreader.LoadFile.isNewVector()) {
                    logFrame.setModel();
                    logreader.LoadFile.setNewVector(false);
                }
            } else {
                clear();
            }
        }
    }

    private static void clear() {
        logFrame.clearModel();
        logFrame.setVisible(false);
        logFrame.removeAll();
        logFrame.dispose();
        logFrame = null;
    }

    public static String getCurrentDirectory() {
        return currentDirectory;
    }

    public static void setCurrentDirectory(String currentDirectory) {
        Logreader.currentDirectory = currentDirectory;
    }

    public static void disableNewFolderButton(Container c) {
        int len = c.getComponentCount();
        for (int i = 0; i < len; i++) {
            Component comp = c.getComponent(i);
            if (comp instanceof JButton) {
                JButton b = (JButton) comp;
                Icon icon = b.getIcon();
                if (icon != null
                        && icon == UIManager.getIcon("FileChooser.newFolderIcon")) {
                    b.setEnabled(false);
                }
            } else if (comp instanceof Container) {
                disableNewFolderButton((Container) comp);
            }
        }
    }
}
