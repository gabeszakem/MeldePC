/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class MyTableCellRender extends DefaultTableCellRenderer {

    public MyTableCellRender() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isCellSelected, boolean hasFocus, int Index_row, int Index_col) {
        try{
        int cols = table.getModel().getColumnCount();
        int IndexModelRow = table.convertRowIndexToModel(Index_row);
        int IndexModelColumn = table.convertColumnIndexToModel(Index_col);
        setBackground((Color) table.getModel().getValueAt(IndexModelRow, (cols-4)));
        setForeground((Color) table.getModel().getValueAt(IndexModelRow, (cols -3)));
        try {
            if (!table.getModel().getValueAt(IndexModelRow, cols -2).equals("")) {
                //System.out.println(table.getModel().getValueAt(IndexModelRow, cols + 2) + "***" + IndexModelColumn);
                if (IndexModelColumn == ((Integer) table.getModel().getValueAt(IndexModelRow, cols -2))) {
                    setToolTipText(table.getModel().getValueAt(IndexModelRow, cols -1).toString());
                    //System.out.println("tooltiptext added: row-" + IndexModelRow + " col-" + IndexModelColumn + " fault-" + table.getModel().getValueAt(IndexModelRow, IndexModelColumn).toString() + " tooltip-" + table.getModel().getValueAt(IndexModelRow, cols + 3).toString());
                } else {
                    setToolTipText(null);
                }
            } else {
                setToolTipText(null);
            }
        } catch (Exception ex) {
            System.out.println(table.getModel().getValueAt(IndexModelRow, cols + 2));
            setToolTipText(null);
        }
        }catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

        if (isCellSelected) {
            //table.setSelectionBackground((Color)table.getModel().getValueAt(Index_row,(cols+2)));
            //table.setSelectionForeground((Color)table.getModel().getValueAt(Index_row,(cols+3)));
        }

        return super.getTableCellRendererComponent(table, value, isCellSelected, hasFocus, Index_row, Index_col);

    }
}
