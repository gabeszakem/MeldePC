/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader.createstruct;

import logreader.struct.ConditionalColorStruct;

/**
 *
 * @author gabesz
 */
public class CreateConditionalColorStruct {

    public ConditionalColorStruct createConditionalColorstruct(
            String column, String conditionalcolumn,
            String conditionalvalue, String match, String conditional,
            String ifbackground, String ifforeground,
            String elsebackground, String elseforeground) {
        ConditionalColorStruct cs = null;
        try {
            int col = Integer.parseInt(column);
            int conditionalcol = Integer.parseInt(conditionalcolumn);
            float conditionalval = new Float(conditionalvalue);
            String myConditional = conditional.toLowerCase();
            cs = new ConditionalColorStruct(col, conditionalcol, conditionalval, match, myConditional,
                    ifbackground, ifforeground, elsebackground, elseforeground);
        } catch (Exception ex) {
            StackTraceElement[] STE = ex.getStackTrace();
            for (int i = STE.length - 1; i >= 0; i--) {
                if (STE[i].getClassName().equals(this.getClass().getName())) {
                    System.out.println(ex+"\t\tclass: "+STE[i].getClassName() + "\t\tline: " + STE[i].getLineNumber());
                }
            }
        }
        return cs;
    }
}
