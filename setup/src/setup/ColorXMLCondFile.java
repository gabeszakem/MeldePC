/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import java.io.File;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author gabesz
 */
public class ColorXMLCondFile {

    private Vector objects = new Vector();

    public ColorXMLCondFile(String path) {

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

                    NodeList columnElmntLst = fstElmnt.getElementsByTagName("column");
                    Element columnElmnt = (Element) columnElmntLst.item(0);
                    NodeList column = columnElmnt.getChildNodes();
                    //System.out.println("Column : " + ((Node) column.item(0)).getNodeValue());

                    NodeList matchElmntLst = fstElmnt.getElementsByTagName("match");
                    Element matchElmnt = (Element) matchElmntLst.item(0);
                    NodeList match = matchElmnt.getChildNodes();
                    //System.out.println("match : " + ((Node) match.item(0)).getNodeValue());

                    NodeList conditionalcolumnElmntLst = fstElmnt.getElementsByTagName("conditionalcolumn");
                    Element conditionalcolumnElmnt = (Element) conditionalcolumnElmntLst.item(0);
                    NodeList conditionalcolumn = conditionalcolumnElmnt.getChildNodes();
                    //System.out.println("conditionalcolumn : " + ((Node) conditionalcolumn.item(0)).getNodeValue());

                    NodeList conditionalElmntLst = fstElmnt.getElementsByTagName("conditional");
                    Element conditionalElmnt = (Element) conditionalElmntLst.item(0);
                    NodeList conditional = conditionalElmnt.getChildNodes();
                    //System.out.println("conditional : " + ((Node) conditional.item(0)).getNodeValue());

                    NodeList conditionalvalueElmntLst = fstElmnt.getElementsByTagName("conditionalvalue");
                    Element conditionalvalueElmnt = (Element) conditionalvalueElmntLst.item(0);
                    NodeList conditionalvalue = conditionalvalueElmnt.getChildNodes();
                    //System.out.println("conditionalvalue : " + ((Node) conditionalvalue.item(0)).getNodeValue());

                    NodeList ifbackgroundElmntLst = fstElmnt.getElementsByTagName("ifbackground");
                    Element ifbackgroundElmnt = (Element) ifbackgroundElmntLst.item(0);
                    NodeList ifbackground = ifbackgroundElmnt.getChildNodes();
                    //System.out.println("ifbackground : " + ((Node) ifbackground.item(0)).getNodeValue());

                    NodeList ifforegroundElmntLst = fstElmnt.getElementsByTagName("ifforeground");
                    Element ifforegroundElmnt = (Element) ifforegroundElmntLst.item(0);
                    NodeList ifforeground = ifforegroundElmnt.getChildNodes();
                    //System.out.println("ifforeground : " + ((Node) ifforeground.item(0)).getNodeValue());

                    NodeList elsebackgroundElmntLst = fstElmnt.getElementsByTagName("elsebackground");
                    Element elsebackgroundElmnt = (Element) elsebackgroundElmntLst.item(0);
                    NodeList elsebackground = elsebackgroundElmnt.getChildNodes();
                    //System.out.println("elsebackground : " + ((Node) elsebackground.item(0)).getNodeValue());

                    NodeList elseforegroundElmntLst = fstElmnt.getElementsByTagName("elseforeground");
                    Element elseforegroundElmnt = (Element) elseforegroundElmntLst.item(0);
                    NodeList elseforeground = elseforegroundElmnt.getChildNodes();
                    //System.out.println("elseforeground : " + ((Node) elseforeground.item(0)).getNodeValue());

                    Vector subVector = new Vector();
                    subVector.add(Integer.toString(s + 1));
                    subVector.add(((Node) column.item(0)).getNodeValue().toString());
                    subVector.add(((Node) match.item(0)).getNodeValue().toString());
                    subVector.add(((Node) conditionalcolumn.item(0)).getNodeValue().toString());

                    if (((Node) conditional.item(0)).getNodeValue().toString().equals("eq")) {
                        subVector.add("==");
                    } else if (((Node) conditional.item(0)).getNodeValue().toString().equals("ne")) {
                        subVector.add("!=");
                    } else if (((Node) conditional.item(0)).getNodeValue().toString().equals("gt")) {
                        subVector.add(">");
                    } else if (((Node) conditional.item(0)).getNodeValue().toString().equals("ge")) {
                        subVector.add(">=");
                    } else if (((Node) conditional.item(0)).getNodeValue().toString().equals("lt")) {
                        subVector.add("<");
                    } else if (((Node) conditional.item(0)).getNodeValue().toString().equals("le")) {
                        subVector.add("<=");
                    } else {
                        subVector.add("");
                    }

                    subVector.add(((Node) conditionalvalue.item(0)).getNodeValue().toString());
                    subVector.add(((Node) ifbackground.item(0)).getNodeValue().toString());
                    subVector.add(((Node) ifforeground.item(0)).getNodeValue().toString());
                    subVector.add(((Node) elsebackground.item(0)).getNodeValue().toString());
                    subVector.add(((Node) elseforeground.item(0)).getNodeValue().toString());
                    objects.add(subVector);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector getObjects() {
        return objects;
    }
}
