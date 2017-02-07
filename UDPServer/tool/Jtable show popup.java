[sourcecode language='java']
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

public class PopUpMenuPanel extends JPanel {

private static final long serialVersionUID = 1L;

JScrollPane pane = new JScrollPane();

JPopupMenu popupMenu = new JPopupMenu();

JTable table;

private static String INSERT_CMD = �Insert Rows�;

private static String DELETE_CMD = �Delete Rows�;

public PopUpMenuPanel() {
super(new GridLayout(1, 0));

table = new JTable(new MyTableModel());
pane = new JScrollPane(table);
add(pane);

JMenuItem menuItem = new JMenuItem(INSERT_CMD);
menuItem.addActionListener(new ActionAdapter(this));
popupMenu.add(menuItem);
menuItem = new JMenuItem(DELETE_CMD);
menuItem.addActionListener(new ActionAdapter(this));
popupMenu.add(menuItem);

MouseListener popupListener = new PopupListener();
table.addMouseListener(popupListener);
table.getTableHeader().addMouseListener(popupListener);
}

private static void createAndShowGUI() {
// Create and set up the window.
JFrame frame = new JFrame(�TableDemo�);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Create and set up the content pane.
PopUpMenuPanel popUp = new PopUpMenuPanel();
popUp.setOpaque(true); // content panes must be opaque
frame.setContentPane(popUp);

// Display the window.
frame.pack();
frame.setVisible(true);
}

public static void main(String[] args) {
// Schedule a job for the event-dispatching thread:
// creating and showing this application�s GUI.
javax.swing.SwingUtilities.invokeLater(new Runnable() {
public void run() {
createAndShowGUI();
}
});
}

public void insertColumn(ActionEvent e) {
JOptionPane.showMessageDialog(this, �Insert Column here.�);
// insert new column here
}

public void deleteColumn(ActionEvent e) {
JOptionPane.showMessageDialog(this, �Delete Column here.�);
// delete column here
}

private class MyTableModel extends AbstractTableModel {
private String[] columnNames = { �First Name�, �Last Name�, �Sport�,
�# of Years�, �Vegetarian� };

private Object[][] data = {
{ �Mary�, �Campione�, �Snowboarding�, new Integer(5),
new Boolean(false) },
{ �Alison�, �Huml�, �Rowing�, new Integer(3), new Boolean(true) },
{ �Kathy�, �Walrath�, �Knitting�, new Integer(2),
new Boolean(false) },
{ �Sharon�, �Zakhour�, �Speed reading�, new Integer(20),
new Boolean(true) },
{ �Philip�, �Milne�, �Pool�, new Integer(10),
new Boolean(false) } };

public final Object[] longValues = { �Sharon�, �Campione�,
�None of the above�, new Integer(20), Boolean.TRUE };

public int getColumnCount() {
return columnNames.length;
}

public int getRowCount() {
return data.length;
}

public String getColumnName(int col) {
return columnNames[col];
}

public Object getValueAt(int row, int col) {
return data[row][col];
}

/*
* JTable uses this method to determine the default renderer/ editor for
* each cell. If we didn�t implement this method, then the last column
* would contain text (�true�/�false�), rather than a check box.
*/
public Class getColumnClass(int c) {
return getValueAt(0, c).getClass();
}

/*
* This method is implemented if we want to make the cell editable.
*/
public boolean isCellEditable(int row, int col) {
if (col < 3) {
return false;
} else {
return true;
}
}

/*
* Implement this method, if your table data will change.
*/
public void setValueAt(Object value, int row, int col) {
data[row][col] = value;
fireTableCellUpdated(row, col);
}
}

class PopupListener implements MouseListener {
public void mousePressed(MouseEvent e) {
showPopup(e);
}

public void mouseReleased(MouseEvent e) {
showPopup(e);
}

private void showPopup(MouseEvent e) {
if (e.isPopupTrigger()) {
popupMenu.show(e.getComponent(), e.getX(), e.getY());
}
}

public void mouseClicked(MouseEvent e) {
}

public void mouseEntered(MouseEvent e) {
}

public void mouseExited(MouseEvent e) {
}
}

} // end of class

class ActionAdapter implements ActionListener {
PopUpMenuPanel adapter;

ActionAdapter(PopUpMenuPanel adapter) {
this.adapter = adapter;
}

public void actionPerformed(ActionEvent e) {
JMenuItem item = (JMenuItem) e.getSource();
if (item.getText() == �Insert Rows�) {
adapter.insertColumn(e);
} else if (item.getText() == �Delete Rows�) {
adapter.deleteColumn(e);
}

}
}