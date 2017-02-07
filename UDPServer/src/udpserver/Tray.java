/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import logreader.LFrame;
import logreader.LoadFile;

/**
 *
 * @author gabeszakem
 */
public class Tray {

    private static LFrame logFrame = null;
    private static PasswordWindow pw = null;

    public static void tray(final udpserver.UDPServer[] us) throws Exception {
        final TrayIcon trayIcon;
        SystemTray tray;
        final Image image, imageStop;
        final FrameStatement fs;
        ActionListener actionListener;

        if (SystemTray.isSupported()) {
            fs = new FrameStatement(us);
            tray = SystemTray.getSystemTray();
            //URL url = Tray.class.getResource("images/log-icon.png");
            image = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("images/log-icon.png"));
            //image = Toolkit.getDefaultToolkit().getImage("images/log-icon.png");
            //imageStop = Toolkit.getDefaultToolkit().getImage("images/log-icon-stop.png");
            imageStop = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("images/log-icon-stop.png"));

            MouseListener mouseListener = new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse clicked!");
                    /*JOptionPane.showMessageDialog(null, "A program indításához dupla bal klikk\n"
                    + "A menü eléréséhez jobb klikk", "UDP Szerver",
                    JOptionPane.INFORMATION_MESSAGE);*/
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse entered!");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse exited!");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse pressed!");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse released!");
                }
            };

            ActionListener showListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //System.out.println("Exiting...");
                    //frame.setVisible(true);
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem item;

            MenuItem logItem = new MenuItem("Log");
            logItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    logFrame();

                }
            });
            popup.add(logItem);
            popup.addSeparator();

            for (int i = 0; i < us.length; i++) {
                item = new MenuItem(us[i].title);
                final JFrame frameCopy = us[i].frame;
                //final JTable tableCurrentView = us[i].tableCurrentView;
                //final JTable tableBrowseBack = us[i].tableBrowseBack;
                //final boolean cwTooltipText = us[i].cwTooltipText;
                //final boolean bbTooltipText = us[i].bbTooltipText;
                item.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (frameCopy.isValid()) {
                            frameCopy.setState(JFrame.ICONIFIED);
                            frameCopy.setVisible(false);
                        } else {
                            frameCopy.setState(JFrame.NORMAL);
                            frameCopy.setVisible(true);
                        }
                    }
                });
                popup.add(item);
            }

            popup.addSeparator();
            MenuItem setupItem = new MenuItem("Setup");
            setupItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    password();
                }
            });
            popup.add(setupItem);


            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(Tools.actualDate("yyyy_MM_dd HH:mm:ss:SS").toString() + " somebody pressed exit");
                    boolean run = false;
                    String message = "";
                    for (int i = 0; i < us.length; i++) {
                        if (us[i].run == true) {
                            run = true;
                            //System.out.println(us[i].port + " is running");
                            try {
                                message += "       " + Integer.toString(us[i].port) + " port is running\n";
                            } catch (NumberFormatException nx) {
                                System.err.println(nx);
                            }
                        } else {
                            //System.out.println(us[i].port + " is stopped");
                        }
                    }
                    //System.out.println("stop is " + run);
                    if (!run) {
                        int optRet = JOptionPane.showConfirmDialog(null, "Biztos leállítod a Szervert?", "Kérdés", JOptionPane.YES_NO_OPTION);
                        if (optRet == JOptionPane.YES_OPTION) {
                            System.exit(0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "A szervert nem lehet leállítani!\n\n"
                                + message + "\n", "Hiba történt!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            popup.addSeparator();
            popup.add(exitItem);

            final PopupMenu myPopup = popup;
            trayIcon = new TrayIcon(image, "", myPopup);
            final javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    boolean itRun = true;
                    for (int i = 0; i < us.length; i++) {
                        if (!us[i].run) {
                            itRun = false;
                        }
                        if ((trayIcon.getImage() == image) && !itRun) {
                            trayIcon.setImage(imageStop);
                        }
                        if ((trayIcon.getImage() == imageStop) && itRun) {
                            trayIcon.setImage(image);
                        }
                    }
                    fs.setLabel(us);
                    logFrameUpdate();

                }
            });
            timer.setRepeats(true);
            timer.start();
            actionListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    /* trayIcon.displayMessage("Action Event",
                    "",
                    TrayIcon.MessageType.INFO);*/

                    if (fs.isVisible()) {
                        fs.dispose();
                    } else {
                        fs.setState(JFrame.NORMAL);
                        fs.setVisible(true);
                    }
                }
            };
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("UDP Szerver: \nPLC és TDC üzenetek fogadására");
            trayIcon.addActionListener(actionListener);
            trayIcon.addMouseListener(mouseListener);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }

        } else {
            //  System Tray is not supported
        }
    }

    private static void password() {
        if (pw != null) {
            if (pw.isVisible()) {
                pw.setVisible(false);
                pw.removeAll();
                pw.dispose();
                pw = null;
            } else {
                pw = new PasswordWindow();
                pw.setVisible(true);
            }
        } else {
            pw = new PasswordWindow();
            pw.setVisible(true);
        }
    }

    private static void logFrame() {
        if (logFrame != null) {
            if (logFrame.isVisible()) {
                clear();
            }
        } else {
            logFrame = new LFrame();
            logFrame.updateTree();
            logFrame.setMaxSize();
            logFrame.setVisible(true);
        }
    }

    private static void logFrameUpdate() {
        if (logFrame != null) {
            if (logFrame.isVisible()) {
                if (LoadFile.isNewVector()) {
                    logFrame.setModel();
                    LoadFile.setNewVector(false);
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
}
