/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author gabesz
 */
public class LogFrame extends LFrame {

    private JPopupMenu popupMenu;

    public LogFrame(String home) {
        super(home);
        initComponents();
    }

    public LogFrame() {
        super();
        initComponents();
    }

    private void popupMenuTree() {
        JMenuItem menuItem = new JMenuItem("select root folder");
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemActionEvent(e);
            }
        });
        popupMenu.add(menuItem);
    }

    private void menuItemActionEvent(java.awt.event.ActionEvent e) {
        try {
            if (e.getActionCommand().compareTo("select root folder") == 0) {
                fileChooser();
            }
        } catch (Exception ex) {
        }
    }

    private void initComponents() {
        popupMenu = new JPopupMenu();
        popupMenuTree();
        //super.tree.setComponentPopupMenu(popupMenu);
        //super.table.setDefaultRenderer(super.getClass(),new MyTableCellRenderer());
    }

    private void fileChooser() {
        FolderSelector fs = new FolderSelector();
        int result = fs.jFileChooser1.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                System.out.println("Approve (Open) was clicked");
                break;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Cancel or the close-dialog icon was clicked");
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("Error");
                break;
        }
        System.out.println(fs.jFileChooser1.getSelectedFile());
        logreader.Logreader.setCurrentDirectory(fs.jFileChooser1.getSelectedFile().toString());
        fs.setVisible(false);
    }
}
