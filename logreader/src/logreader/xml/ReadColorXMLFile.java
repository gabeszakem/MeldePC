/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader.xml;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import logreader.struct.ColorStruct;
import logreader.createstruct.CreateColorstruct;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author gabesz
 */
public class ReadColorXMLFile {

    private ArrayList colors = new ArrayList();
    private CreateColorstruct ccs = new CreateColorstruct();

    public ReadColorXMLFile(String path) {

        try {
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("color");

            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element fstElmnt = (Element) fstNode;

                    NodeList columnElmntLst = fstElmnt.getElementsByTagName("column");
                    Element columnElmnt = (Element) columnElmntLst.item(0);
                    NodeList column = columnElmnt.getChildNodes();
                    //System.out.println("Column : " + ((Node) column.item(0)).getNodeValue());
                    NodeList matchElmntLst = fstElmnt.getElementsByTagName("match");
                    Element matchElmnt = (Element) matchElmntLst.item(0);
                    NodeList match = matchElmnt.getChildNodes();
                    //System.out.println("match : " + ((Node) match.item(0)).getNodeValue());
                    NodeList backgroundElmntLst = fstElmnt.getElementsByTagName("background");
                    Element backgroundElmnt = (Element) backgroundElmntLst.item(0);
                    NodeList background = backgroundElmnt.getChildNodes();
                    //System.out.println("background : " + ((Node) background.item(0)).getNodeValue());
                    NodeList foregroundElmntLst = fstElmnt.getElementsByTagName("foreground");
                    Element foregroundElmnt = (Element) foregroundElmntLst.item(0);
                    NodeList foreground = foregroundElmnt.getChildNodes();
                    //System.out.println("foreground : " + ((Node) foreground.item(0)).getNodeValue());
                    ColorStruct cs = ccs.createColorstruct((String) column.item(0).getNodeValue(), (String) match.item(0).getNodeValue(), (String) background.item(0).getNodeValue(), (String) foreground.item(0).getNodeValue());
                    if (cs != null) {
                        colors.add(cs);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList getColors() {
        return colors;
    }
}
