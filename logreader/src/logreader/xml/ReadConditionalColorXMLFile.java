/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader.xml;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import logreader.struct.ConditionalColorStruct;
import logreader.createstruct.CreateConditionalColorStruct;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author gabesz
 */
public class ReadConditionalColorXMLFile {

    private ArrayList colors = new ArrayList();
    private CreateConditionalColorStruct ccs = new CreateConditionalColorStruct();

    public ReadConditionalColorXMLFile(String path) {

        try {
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("colorconditional");

            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element fstElmnt = (Element) fstNode;

                    NodeList column = getNodeList(fstElmnt, "column");
                    //System.out.println("Column : " + ((Node) column.item(0)).getNodeValue());
                    NodeList match = getNodeList(fstElmnt, "match");
                    //System.out.println("match : " + ((Node) match.item(0)).getNodeValue());
                    NodeList conditionalcolumn = getNodeList(fstElmnt, "conditionalcolumn");
                    //System.out.println("conditionalcolumn : " + ((Node) conditionalcolumn.item(0)).getNodeValue());
                    NodeList conditional = getNodeList(fstElmnt, "conditional");
                    //System.out.println("conditional : " + ((Node) conditional.item(0)).getNodeValue());
                    NodeList conditionalvalue = getNodeList(fstElmnt, "conditionalvalue");
                    //System.out.println("conditionalvalue : " + ((Node) conditionalvalue.item(0)).getNodeValue());
                    NodeList ifbackground = getNodeList(fstElmnt, "ifbackground");
                    //System.out.println("ifbackground : " + ((Node) ifbackground.item(0)).getNodeValue());
                    NodeList ifforeground = getNodeList(fstElmnt, "ifforeground");
                    //System.out.println("ifforeground : " + ((Node) ifforeground.item(0)).getNodeValue());
                    NodeList elsebackground = getNodeList(fstElmnt, "elsebackground");
                    //System.out.println("elsebackground : " + ((Node) elsebackground.item(0)).getNodeValue());
                    NodeList elseforeground = getNodeList(fstElmnt, "elseforeground");
                    //System.out.println("elseforeground : " + ((Node) elseforeground.item(0)).getNodeValue());
                    
                    ConditionalColorStruct cs = ccs.createConditionalColorstruct(
                            (String) column.item(0).getNodeValue(), 
                            (String) conditionalcolumn.item(0).getNodeValue(),
                            (String) conditionalvalue.item(0).getNodeValue(),
                            (String) match.item(0).getNodeValue(), 
                            (String) conditional.item(0).getNodeValue(), 
                            (String) ifbackground.item(0).getNodeValue(), 
                            (String) ifforeground.item(0).getNodeValue(), 
                            (String) elsebackground.item(0).getNodeValue(), 
                            (String) elseforeground.item(0).getNodeValue());
                    if (cs != null) {
                        colors.add(cs);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList getColors() {
        return colors;
    }
    
    private NodeList getNodeList(Element fstElmnt, String myNode){
        NodeList columnElmntLst = fstElmnt.getElementsByTagName(myNode);
        Element columnElmnt = (Element) columnElmntLst.item(0);
        return columnElmnt.getChildNodes();
    }
}
