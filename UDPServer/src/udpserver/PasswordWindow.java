/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PasswordWindow.java
 *
 * Created on 2011.06.14., 20:31:37
 */
package udpserver;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import setup.SetupFrame;

/**
 *
 * @author gabesz
 */
public class PasswordWindow extends javax.swing.JFrame {

    /** Creates new form PasswordWindow */
    public PasswordWindow() {
        initComponents();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int Width = screenSize.width;
        int Height = screenSize.height;

        setLocation((Width - getWidth()) / 2, ((Height - getHeight()) / 2));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Password Window");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(Toolkit.getDefaultToolkit().getImage(PasswordWindow.class.getResource("images/log-icon16.png")));
        setMinimumSize(new java.awt.Dimension(250, 220));
        setName("Jelszó"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(null);

        jPasswordField1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jPasswordField1.setForeground(java.awt.Color.blue);
        jPasswordField1.setOpaque(false);
        getContentPane().add(jPasswordField1);
        jPasswordField1.setBounds(20, 40, 210, 28);
        jPasswordField1.addKeyListener(new MyKeyListener());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel1.setText("Enter Password:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 10, 171, 20);

        jButton1.setText("OK");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(20, 80, 100, 30);

        jButton2.setText("cancel");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(130, 80, 100, 30);
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 30, 250, 90);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        char[] input = jPasswordField1.getPassword();
        if (isPasswordCorrect(input)) {
            SetupFrame setupFrame = new SetupFrame();
            setupFrame.setTitle("setup");
            setupFrame.setMaxSize();
            setupFrame.setVisible(true);
        }
        Arrays.fill(input, '0');
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new PasswordWindow().setVisible(true);
            }
        });
    }

    private static boolean isPasswordCorrect(char[] input) {
        boolean isCorrect = true;
        char[] correctPassword = {'m', 'e', 'l', 'd', 'e'};

        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals(input, correctPassword);
        }

        //Zero out the password.
        Arrays.fill(correctPassword, '0');

        return isCorrect;
    }

    public class MyKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int i = keyEvent.getKeyCode();
            if (i == 10) {
                jButton1.doClick();
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration//GEN-END:variables
}