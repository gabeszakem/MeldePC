/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import logreader.struct.ToolTipStruct;
import logreader.struct.ConditionalColorStruct;
import logreader.struct.ColorStruct;
import logreader.xml.ReadDrivesFaultXMLFile;
import logreader.xml.ReadConditionalColorXMLFile;
import logreader.xml.ReadColorXMLFile;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author gkovacs02
 */
public class Colorize {

    private ReadColorXMLFile cxml;
    private ReadConditionalColorXMLFile ccxml;
    private ReadDrivesFaultXMLFile dfxml;
    private ArrayList colors;
    private ArrayList conditionalColors;
    private ToolTipStruct df;
    @SuppressWarnings("FieldMayBeFinal")
    private Color backgoundColor;
    @SuppressWarnings("FieldMayBeFinal")
    private Color foregoundColor;
    @SuppressWarnings("FieldMayBeFinal")
    private Color gridColor;
    @SuppressWarnings("FieldMayBeFinal")
    private Color selectionBackGround;
    @SuppressWarnings("FieldMayBeFinal")
    private Color selectionForeGround;
    private int origsize;
    private boolean enableToolTipText;

    public Colorize() {
        reInitXML();
        backgoundColor = Color.WHITE;
        foregoundColor = Color.BLACK;
        gridColor = Color.BLUE;
        selectionBackGround = Color.LIGHT_GRAY;
        selectionForeGround = Color.BLACK;
        enableToolTipText = true;
    }

    @SuppressWarnings("UseOfObsoleteCollectionType")
    public Vector calculateColors(Vector v) {
        @SuppressWarnings("UnusedAssignment")
        int compColumnIndex = -1;
        origsize = v.size();
        for (int index = 0; index <= colors.size(); index++) {
            if (index < colors.size()) {
                compColumnIndex = ((ColorStruct) colors.get(index)).Index_col;

                //System.out.println("I: "+index+" K: "+k+" Text: "+compColumn+" compColumnIndex: "+compColumnIndex+" Index_row: "+Index_row +" Index_col: "+Index_col);
                if (v.get(compColumnIndex).toString().toLowerCase().matches(((ColorStruct) colors.get(index)).match)) {
                    v.add(((ColorStruct) colors.get(index)).background);
                    v.add(((ColorStruct) colors.get(index)).foreground);
                    //v.add(((ColorStruct) colors.get(index)).selectionBackground);
                    //v.add(((ColorStruct) colors.get(index)).selectionForeground);
                    break;
                }
            } else {

                for (int i = 0; i < conditionalColors.size(); i++) {
                    compColumnIndex = ((ConditionalColorStruct) conditionalColors.get(i)).Index_col;
                    int compConditionalcolumnIndex = ((ConditionalColorStruct) conditionalColors.get(i)).conditionalcolumn;

                    if ((compColumnIndex != -1) && compConditionalcolumnIndex != -1 & v.get(compColumnIndex).toString().toLowerCase().matches(((ConditionalColorStruct) conditionalColors.get(i)).match)) {
                        try {
                            String s = v.get(compConditionalcolumnIndex).toString();
                            if (!s.trim().isEmpty()) {
                                float f;
                                if (s.toUpperCase().equals("+0.000000 E-01".toUpperCase())) {
                                    f = 0f;
                                } else {
                                    try{
                                    f = new Float(s.replace(" ", ""));
                                    }catch(NumberFormatException e){
                                        e.printStackTrace(System.err);
                                        f=-1f;
                                    }
                                }
                                if ((((ConditionalColorStruct) conditionalColors.get(i)).conditional.equals("eq") & f == ((ConditionalColorStruct) conditionalColors.get(i)).conditionalvalue)
                                        || (((ConditionalColorStruct) conditionalColors.get(i)).conditional.equals("ne") & f != ((ConditionalColorStruct) conditionalColors.get(i)).conditionalvalue)
                                        || (((ConditionalColorStruct) conditionalColors.get(i)).conditional.equals("gt") & f > ((ConditionalColorStruct) conditionalColors.get(i)).conditionalvalue)
                                        || (((ConditionalColorStruct) conditionalColors.get(i)).conditional.equals("ge") & f >= ((ConditionalColorStruct) conditionalColors.get(i)).conditionalvalue)
                                        || (((ConditionalColorStruct) conditionalColors.get(i)).conditional.equals("lt") & f < ((ConditionalColorStruct) conditionalColors.get(i)).conditionalvalue)
                                        || (((ConditionalColorStruct) conditionalColors.get(i)).conditional.equals("le") & f <= ((ConditionalColorStruct) conditionalColors.get(i)).conditionalvalue)) {
                                    v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifbackground);
                                    v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifforeground);
                                    //v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifselectionBackground);
                                    //v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifselectionForeground);
                                    break;
                                } else {
                                    v.add(((ConditionalColorStruct) conditionalColors.get(i)).elsebackground);
                                    v.add(((ConditionalColorStruct) conditionalColors.get(i)).elseforeground);
                                    //v.add(((ConditionalColorStruct) conditionalColors.get(i)).elseselectionBackground);
                                    //v.add(((ConditionalColorStruct) conditionalColors.get(i)).elseselectionForeground);
                                }
                            } else {
                                v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifbackground);
                                v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifforeground);
                                //v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifselectionBackground);
                                //v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifselectionForeground);
                            }
                        } catch (Exception Ex) {
                            Ex.printStackTrace(System.err);
                            v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifbackground);
                            v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifforeground);
                            //v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifselectionBackground);
                            //v.add(((ConditionalColorStruct) conditionalColors.get(i)).ifselectionForeground);
                        }
                    }
                }
            }
        }
        if (v.size() == origsize) {
            //System.out.println(origsize);
            v.add(backgoundColor);
            v.add(foregoundColor);
            //v.add(selectionBackGround);
            //v.add(selectionForeGround);
        }
        origsize=v.size();
        if (enableToolTipText) {
            v = toolTipText(v);
        }
        if (v.size() == origsize){
            v.add("");
            v.add("");
        }

        return v;
    }

    private void reInitXML() {
        String separator = System.getProperty("file.separator");
        String pathDir = System.getProperty("user.dir") + separator + "logreaderxml";
        String path = pathDir + separator + "colors.xml";
        String condPath = pathDir + separator + "conditionalcolor.xml";
        String driveFaultPath = pathDir + separator + "tooltip.xml";
        cxml = new ReadColorXMLFile(path);
        ccxml = new ReadConditionalColorXMLFile(condPath);
        dfxml = new ReadDrivesFaultXMLFile(driveFaultPath);
        colors = cxml.getColors();
        conditionalColors = ccxml.getColors();
        df = dfxml.getToolTip();
    }

    @SuppressWarnings("UseOfObsoleteCollectionType")
    private Vector toolTipText(Vector v) {
        if (!v.get(df.column).toString().trim().isEmpty()) {
            String s = v.get(df.column).toString();
            if (s.equals("+0.000000 E-01")) {
                s = "0";
            }
            try {
                float f;
                try{
                f = new Float(s.replace(" ", ""));
                }catch(NumberFormatException e){
                    f=-1f;
                    e.printStackTrace(System.err);
                }
                //System.out.println(f);
                v.set(df.column, f);
                s = v.get(df.textColumn).toString();
                if (s.toLowerCase().matches(df.textValue)) {
                    //System.out.println(v.get(df.column));
                    v.add(df.column);
                    v.add(ToolTip.toolTip(f));
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
                System.err.println(ex);
                System.err.println(s);
            }

        }
        return v;
    }

    public void setEnableToolTipText(boolean enableToolTipText) {
        this.enableToolTipText = enableToolTipText;
    }
}
