/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import logreader.xml.ReadXMLFile;
import java.util.ArrayList;

/**
 *
 * @author gabesz
 */
public class ToolTip {

    private static String path;
    private static final String tagName = "faults";
    private static String toolTipString = "";
    static ArrayList missingList = new ArrayList();

    public static String toolTip(float f) {
        try {
            String separator = System.getProperty("file.separator");
            String pathDir = System.getProperty("user.dir") + separator + "logreaderxml";
            path = pathDir + separator + "drivesfaults.xml";
            String tagValue = "F" + Integer.toString((int) f);
            //System.out.println(tagValue);

            ReadXMLFile x = new ReadXMLFile(path, tagName, tagValue);
            if (!x.getValue().equals("null")) {
                toolTipString = htmlString(x.getValue());
            } 
        } catch (Exception Ex) {
            toolTipString = "";
        } finally {
            return toolTipString;
        }
    }

    private static String htmlString(String htmlString) {
        return "<html><center>" + htmlString + "</center></html>";
    }
}
