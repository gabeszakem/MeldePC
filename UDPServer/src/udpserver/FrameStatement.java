/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

/**
 *
 * @author gkovacs02
 */
public class FrameStatement extends JFrame {

    private ArrayList jLabelList = new ArrayList();

    public FrameStatement(final udpserver.UDPServer[] us) {
        JPanel mainPanel = new JPanel();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        setResizable(false);
        setTitle("UDP Szerver");
        setIconImage(Toolkit.getDefaultToolkit().getImage(FrameStatement.class.getResource("images/log-icon16.png")));
        //setIconImage(Toolkit.getDefaultToolkit().getImage("images/log-icon16.png"));
        int frameWidth = 660;
        int frameHeight = 35+(us.length * 53);
        setSize(frameWidth, frameHeight);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int Width = screenSize.width;
        int Height = screenSize.height;
        /*System.out.println(frameWidth + " * " + frameHeight + " " + Width + " * " + Height + " "
                + (Width - frameWidth) / 2 + "*" + (Height - frameHeight / 2));*/
        this.setLocation((Width - frameWidth) / 2, ((Height - frameHeight) / 2));

        //mainPanel.setLayout(null);
        for (int i = 0; i < us.length; i++) {

            SubPanel subPanel = new SubPanel();
            subPanel.jLabel1.setText(us[i].title);
            if (us[i].run) {
                subPanel.jLabel1.setForeground(Color.BLACK);
            } else {
                subPanel.jLabel1.setForeground(Color.RED);
            }
            final JButton bStart = us[i].buttonStart;
            final JButton bStop = us[i].buttonStop;
            final udpserver.UDPServer u = us[i];
            subPanel.startButton.setBackground(Color.GREEN);
            subPanel.startButton.setText("start");
            subPanel.startButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    bStart.doClick();
                }
            });
            subPanel.stopButton.setBackground(Color.RED);
            subPanel.stopButton.setText("stop");
            subPanel.stopButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    bStop.doClick();
                }
            });
            subPanel.showButton.setText("show");
            subPanel.showButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    u.frame.setState(JFrame.NORMAL);
                    u.frame.setVisible(true);
                }
            });
            subPanel.hideButton.setText("hide");
            subPanel.hideButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    u.frame.setState(JFrame.ICONIFIED);
                    u.frame.setVisible(false);
                }
            });
            jLabelList.add(subPanel.jLabel1);
            mainPanel.add(subPanel);
        }
        add(mainPanel);
        setVisible(false);
    }

    public void setLabel(final udpserver.UDPServer[] us) {
        for (int id = 0; id < jLabelList.size(); id++) {
            JLabel label = (JLabel) jLabelList.get(id);
            if (us[id].run) {
                label.setForeground(Color.BLACK);
            } else {
                label.setForeground(Color.RED);
            }
        }
    }

    @Override
    public void dispose() {
        setState(JFrame.ICONIFIED);
        //System.out.println("FrameStatement closed");
        super.dispose();
    }

}
