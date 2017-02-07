/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader.createstruct;

import logreader.struct.ColorStruct;

/**
 *
 * @author gabesz
 */
public class CreateColorstruct {
    public ColorStruct createColorstruct(String column, String match, String background,String foreground){
        ColorStruct cs=null;
        try{
            int col = Integer.parseInt(column);
            cs = new ColorStruct (col, match, background, foreground);
        }catch(Exception ex){
            System.out.println(ex);
        }
        return cs;
    }
}