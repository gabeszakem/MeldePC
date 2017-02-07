/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

/**
 *
 * @author gabesz
 */
public class ExcelWriterWithApachePOI {

    private String fileName;
    private ArrayList styles;
    private javax.swing.JFileChooser jFileChooser;
    private String currentDirectory;

    public ExcelWriterWithApachePOI(String path, JTable table) {
        Date systemDate = new Date();
        String date = systemDate.toString().trim();
        date = date.replace(":", "_").replace(":", "_").replace(" ", "_");
        System.out.println("date: " + date);
        fileName = date + ".xls";
        currentDirectory=path + fileName;
         FolderSelectorfile fs = new FolderSelectorfile();
         //disableNewFolderButton(fs);
            int result = fs.jFileChooser1.showOpenDialog(null);
            switch (result) {
                    case JFileChooser.APPROVE_OPTION:
                        System.out.println("Approve (Save) was clicked");
                        currentDirectory = fs.jFileChooser1.getSelectedFile().toString();
                        fs.setVisible(false);
                        fs = null;
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        System.out.println("Cancel or the close-dialog icon was clicked");
                        fs.setVisible(false);
                        fs = null;
                        //System.exit(0);
                        break;
                    case JFileChooser.ERROR_OPTION:
                        System.out.println("Error");
                        //System.exit(0);
                        break;
                }
        
        
        System.out.println(Thread.currentThread().getClass().getClassLoader());
        table.selectAll();

        int numrows = table.getSelectedRowCount();
        int numcols = table.getSelectedColumnCount();
        int[] rowsselected = table.getSelectedRows();
        int[] colsselected = table.getSelectedColumns();

        ListSelectionModel listModel = table.getSelectionModel();
        listModel.removeSelectionInterval(listModel.getMinSelectionIndex(), listModel.getMaxSelectionIndex());
        styles = new ArrayList();

        try {
            Workbook wb = new HSSFWorkbook();
            String safeName = WorkbookUtil.createSafeSheetName("Melde");
            Sheet sheet = wb.createSheet(safeName);
            Row rowHeader = sheet.createRow(0);

            for (int i = 0; i < numrows; i++) {

                Row row = sheet.createRow(i + 1);

                TableCellRenderer tcr = table.getCellRenderer(rowsselected[i], colsselected[0]);
                JComponent c = (JComponent) tcr.getTableCellRendererComponent(table, JTable.class, false, false, rowsselected[i], colsselected[0]);
                short backgroundColor = ExcelColorSupport.getNearestColor(c.getBackground());
                short foregroundColor = ExcelColorSupport.getNearestColor(c.getForeground());
                int index = 0;
                boolean newStyle = true;
                if (styles.size() > 0) {
                    for (int k = 0; k < styles.size(); k++) {
                        if ((((Style) styles.get(k)).backgroundColor == backgroundColor) && (((Style) styles.get(k)).foregroundColor == foregroundColor)) {
                            newStyle = false;
                            index = k;
                            break;
                        }
                    }
                } else {
                    newStyle = true;
                }

                if (newStyle) {
                    styles.add(new Style(backgroundColor, foregroundColor, wb));
                    index = styles.size() - 1;
                }

                //style.setFillBackgroundColor();

                for (int j = 0; j < numcols; j++) {

                    Cell cell = row.createCell(j);
                    String text = table.getValueAt(rowsselected[i], colsselected[j]).toString();
                    cell.setCellValue(text);

                    TableCellRenderer tableCellRenderer = table.getCellRenderer(rowsselected[i], colsselected[j]);
                    JComponent component = (JComponent) tableCellRenderer.getTableCellRendererComponent(table, JTable.class, false, false, rowsselected[i], colsselected[j]);
                    String toolTip = component.getToolTipText();


                    if (toolTip != null) {
                        String[] split = toolTip.split("</center></html>");
                        split = split[0].split("<html><center>");

                        if (split.length == 2) {
                            // When the comment box is visible, have it show in a 1x3 space
                            CreationHelper factory = wb.getCreationHelper();
                            Drawing drawing = sheet.createDrawingPatriarch();
                            ClientAnchor anchor = factory.createClientAnchor();
                            anchor.setCol1(j);
                            anchor.setCol2(j + 2);
                            anchor.setRow1(i);
                            anchor.setRow2(i + 2);

                            // Create the comment and set the text+author
                            Comment comment = drawing.createCellComment(anchor);
                            RichTextString str = factory.createRichTextString(split[1]);
                            comment.setString(str);
                            comment.setAuthor("Apache POI");

                            // Assign the comment to the cell
                            cell.setCellComment(comment);
                        }
                    }
                    cell.setCellStyle(((Style) styles.get(index)).style);
                }
            }
            //String[] columnsLetter = new String[]{"A","B","C","D","E","F"};

            for (int i = 0; i < table.getColumnCount(); i++) {

                Cell cellHeader = rowHeader.createCell(i);
                cellHeader.setCellValue(table.getColumnName(i));

                // Style the cell with borders all around.
                CellStyle styleHeader = wb.createCellStyle();
                cellHeader.setCellStyle(setBorder(styleHeader));
                sheet.autoSizeColumn(i);

                styleHeader = setColor(styleHeader, Color.gray);
                // Create a new font and alter it.
                Font stylefont = wb.createFont();
                stylefont.setColor(ExcelColorSupport.getNearestColor(Color.white));
                styleHeader.setFont(stylefont);
            }
            String s = CellReference.convertNumToColString(numcols - 1);
            String filter = "A1:" + s + Integer.toString(numrows - 1);
            System.out.println(filter);
            sheet.setAutoFilter(CellRangeAddress.valueOf(filter));


            //sheet.setAutoFilter(CellRangeAddress.valueOf("C5:F200"));
            FileOutputStream fileOut = new FileOutputStream(currentDirectory);
            wb.write(fileOut);
            fileOut.close();
            System.out.println("Excel ready");

            JOptionPane.showMessageDialog(null, "Az exportálás sikerült:\n" + currentDirectory,
                    "Az exportálás sikerült",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Az exportálás nem sikerült",
                    "Az exportálás nem sikerült:\n",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//Write excel file from jtable Used functions: cell color, border header,comment(tooltiptext)

    private CellStyle setBorder(CellStyle style) {
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }// Style the cell with borders all around.

    private CellStyle setColor(CellStyle style, Color background) {
        style.setFillForegroundColor(ExcelColorSupport.getNearestColor(background));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return style;
    }//Set cell background color 

    private class Style {

        private short backgroundColor;
        private short foregroundColor;
        private CellStyle style;

        private Style(short backgroundColor, short foregroundColor, Workbook wb) {
            this.backgroundColor = backgroundColor;
            this.foregroundColor = foregroundColor;
            Font font = wb.createFont();
            font.setColor(foregroundColor);
            this.style = wb.createCellStyle();
            this.style.setFillForegroundColor(backgroundColor);
            this.style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            this.style.setFont(font);
            this.style = setBorder(this.style);
        }
    }//Struct style. Create new style. 
    
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
}//Write excel file from jtable Used functions: cell color, border header,comment(tooltiptext)
