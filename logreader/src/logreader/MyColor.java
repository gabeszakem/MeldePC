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
public class MyColor {

    public static Color lookupColor(String color) {
        String s = color.toLowerCase();
        if (s.equals("black")) {
            return Color.black;
        } else if (s.equals("blue")) {
            return Color.blue;
        } else if (s.equals("cyan")) {
            return Color.cyan;
        } else if (s.equals("darkgray")) {
            return Color.darkGray;
        } else if (s.equals("darkgrey")) {
            return Color.darkGray;
        } else if (s.equals("gray")) {
            return Color.gray;
        } else if (s.equals("grey")) {
            return Color.gray;
        } else if (s.equals("green")) {
            return Color.green;
        } else if (s.equals("lightgray")) {
            return Color.lightGray;
        } else if (s.equals("lightgrey")) {
            return Color.lightGray;
        } else if (s.equals("magenta")) {
            return Color.magenta;
        } else if (s.equals("orange")) {
            return Color.orange;
        } else if (s.equals("pink")) {
            return Color.pink;
        } else if (s.equals("red")) {
            return Color.red;
        } else if (s.equals("white")) {
            return Color.white;
        } else if (s.equals("yellow")) {
            return Color.yellow;
        } else {
            return Color.black;
        }
    }
}
