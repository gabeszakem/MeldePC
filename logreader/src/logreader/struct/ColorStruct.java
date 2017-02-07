/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader.struct;

import java.awt.Color;
import logreader.InverseColor;
import logreader.MyColor;

/**
 *
 * @author gabesz
 */


public class ColorStruct {
    public int Index_col;
    public String match;
    public Color background;
    public Color foreground;
    public Color selectionBackground;
    public Color selectionForeground;
    public ColorStruct (int Index_col,String match,String background, String foreground){
        this.Index_col = Index_col;
        this.match="(?i).*"+match+".*";
        this.background=MyColor.lookupColor(background);
        this.foreground=MyColor.lookupColor(foreground);
        this.selectionBackground = InverseColor.inverseColor(this.background);
        this.selectionForeground = InverseColor.inverseColor(this.foreground);
    }
}
