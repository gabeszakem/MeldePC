/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import java.awt.Color;

/**
 *
 * @author gabesz
 */
public class InverseColor {
    public static Color inverseColor(Color color){
        return Color.getColor(null, color.getRGB() ^ 0xffffff);
    }
}
