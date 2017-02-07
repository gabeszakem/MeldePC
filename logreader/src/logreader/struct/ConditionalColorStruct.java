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
public class ConditionalColorStruct {

    public int Index_col, conditionalcolumn;
    public float conditionalvalue;
    public String match, conditional;
    public Color ifbackground, elsebackground;
    public Color ifforeground, elseforeground;
    public Color ifselectionBackground, elseselectionBackground;
    public Color ifselectionForeground, elseselectionForeground;

    public ConditionalColorStruct(int Index_col, int conditionalcolumn, float conditionalvalue,
            String match, String conditional, String ifbackground,
            String ifforeground, String elsebackground, String elseforeground) {
        this.Index_col = Index_col;
        this.conditionalcolumn = conditionalcolumn;
        this.conditionalvalue = conditionalvalue;
        this.match = "(?i).*" + match + ".*";
        this.conditional = conditional.toLowerCase();
        this.ifbackground = MyColor.lookupColor(ifbackground);
        this.ifforeground = MyColor.lookupColor(ifforeground);
        this.ifselectionBackground = InverseColor.inverseColor(this.ifbackground);
        this.ifselectionForeground = InverseColor.inverseColor(this.ifforeground);
        this.elsebackground = MyColor.lookupColor(elsebackground);
        this.elseforeground = MyColor.lookupColor(elseforeground);
        this.elseselectionBackground = InverseColor.inverseColor(this.elsebackground);
        this.elseselectionForeground = InverseColor.inverseColor(this.elseforeground);
    }
}
