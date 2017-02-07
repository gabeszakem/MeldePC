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
import udpserver.logreader.LFrame;
import udpserver.logreader.LoadFile;

/**
 *
 * @author gabeszakem
 */
public class Tray {

    public static void tray(final udpserver.UDPServer[] us) throws Exception {
        final TrayIcon trayIcon;
        SystemTray tray;
        final Image image, imageStop;
        final FrameStatement fs;
        ActionListener actionListener;
        //final LogFrame logFrame;
        final LFrame logFrame;
        
        if (SystemTray.isSupported()) {
            fs = new FrameStatement(us);
            tray = SystemTray.getSystemTray();
            image = Toolkit.getDefaultToolkit().getImage("images/log-icon.png");
            imageStop = Toolkit.getDefaultToolkit().getImage("images/log-icon-stop.png");
            //logFrame = new LogFrame();
            logFrame = new LFrame();

            MouseListener mouseListener = new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Tray Icon - Mouse clicked!");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    System.out.println("Tray Icon - Mouse entered!");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    System.out.println("Tray Icon - Mouse exited!");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("Tray Icon - Mouse pressed!");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse released!");
                }
            };

            ActionListener showListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Exiting...");
                    //frame.setVisible(true);
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem item;

            MenuItem logItem = new MenuItem("Log");
            logItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (logFrame.isVisible()) {
                        logFrame.setVisible(false);
                    } else {

                        logFrame.setMaxSize();
                        logFrame.setModel();
                        logFrame.setVisible(true);
                    }
                }
            });
            popup.add(logItem);
            popup.addSeparator();

            for (int i = 0; i < us.length; i++) {
                item = new MenuItem(us[i].title);
                final JFrame frameCopy = us[i].frame;
                item.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (frameCopy.isValid()) {
                            frameCopy.setVisible(false);
                        } else {
                            frameCopy.setVisible(true);
                        }
                    }
                });
                popup.add(item);
            }

            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean run = false;
                    String message = "";
                    for (int i = 0; i < us.length; i++) {
                        if (us[i].run == true) {
                            run = true;
                            System.out.println(us[i].port + " is running");
                            try {
                                message += "       " + Integer.toString(us[i].port) + " port is running\n";
                            } catch (NumberFormatException nx) {
                                System.err.println(nx);
                            }
                        } else {
                            System.out.println(us[i].port + " is stopped");
                        }
                    }
                    System.out.println("stop is " + run);
                    if (!run) {
                        int optRet = JOptionPane.showConfirmDialog(null, "Biztos leállítod a Szervert?", "Kérdés", JOptionPane.YES_NO_OPTION);
                        if (optRet == JOptionPane.YES_OPTION) {
                            System.exit(0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "A szervert nem lehet leállítani!\n\n" + message + "\n");
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
                    if (logFrame.isVisible()) {
                        if (LoadFile.isNewVector()) {
                            logFrame.setModel();
                            LoadFile.setNewVector(false);
                        }
                    }
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
                        fs.setVisible(false);
                    } else {
                        fs.setVisible(true);
                    }
                }
            };
            trayIcon.setImageAutoSize(true);
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
}
