/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import logreader.struct.ToolTipStruct;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author gabesz
 */
public class ReadDrivesFaultXMLFile {

    private ToolTipStruct tts;

    public ReadDrivesFaultXMLFile(String path) {

        try {
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("setup");

            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element fstElmnt = (Element) fstNode;

                    NodeList columnElmntLst = fstElmnt.getElementsByTagName("column");
                    Element columnElmnt = (Element) columnElmntLst.item(0);
                    NodeList column = columnElmnt.getChildNodes();
                    //System.out.println("Column : " + ((Node) column.item(0)).getNodeValue());
                    NodeList textcolumnElmntLst = fstElmnt.getElementsByTagName("textcolumn");
                    Element textcolumnElmnt = (Element) textcolumnElmntLst.item(0);
                    NodeList textcolumn = textcolumnElmnt.getChildNodes();
                    //System.out.println("match : " + ((Node) match.item(0)).getNodeValue());
                    NodeList textvalueElmntLst = fstElmnt.getElementsByTagName("textvalue");
                    Element textvalueElmnt = (Element) textvalueElmntLst.item(0);
                    NodeList textvalue = textvalueElmnt.getChildNodes();
                    //System.out.println("background : " + ((Node) background.item(0)).getNodeValue());
                    try {
                        int col = Integer.parseInt((String) column.item(0).getNodeValue());
                        int textCol = Integer.parseInt((String) textcolumn.item(0).getNodeValue());
                        tts = new ToolTipStruct(col, textCol, (String) textvalue.item(0).getNodeValue());
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ToolTipStruct getToolTip() {
        return tts;
    }
}
