/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader.struct;

/**
 *
 * @author gabesz
 */

public class ToolTipStruct {
    public int column;
    public int textColumn;
    public String textValue;
    public ToolTipStruct (int column,int textColumn, String textValue){
        this.column = column;
        this.textColumn=textColumn;
        this.textValue="(?i).*"+textValue+".*";
    }
}
