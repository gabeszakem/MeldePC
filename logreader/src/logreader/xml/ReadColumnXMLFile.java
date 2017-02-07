/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class read columns from xml
 * @author gkovacs02
 */
public class ReadColumnXMLFile {

    private ArrayList ITEMS;//coluns from xml
    private String[] HIDDENCOLUMNS = new String[]{
        "bground",
        "fground",
        "tooltipcolumn",
        "tooltiptext"
    };//Additional columns for colors and tooltiptext

    /**
     * Read calumns from xml file
     * Parameter path the place where we can find xml
     * @param path 
     */
    public ReadColumnXMLFile(String path) {

        try {
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("columns");
            ITEMS = new ArrayList();
            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element fstElmnt = (Element) fstNode;

                    NodeList columnElmntLst = fstElmnt.getElementsByTagName("column");
                    Element columnElmnt = (Element) columnElmntLst.item(0);
                    NodeList column = columnElmnt.getChildNodes();
                    //System.out.println("Column : " + ((Node) column.item(0)).getNodeValue());
                    String newColumn = (String) column.item(0).getNodeValue();//New column from xml
                    boolean isNewColumn = true;
                    for (int i = 0; i < HIDDENCOLUMNS.length; i++) {
                        if (HIDDENCOLUMNS[i].equals(newColumn)) {
                            isNewColumn = false;
                            break;
                        }
                    }
                    if (isNewColumn) {
                        ITEMS.add(newColumn);
                    }
                }
            }
            ITEMS.addAll(Arrays.asList(HIDDENCOLUMNS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return columns in Arraylist
     * @return 
     */
    public ArrayList getItems() {
        return ITEMS;
    }

    /**
     * Return columns in String[]
     * @return 
     */
    public String[] getItemsString() {
        String[] itemsString = new String[ITEMS.size()];
        for (int i = 0; i < ITEMS.size(); i++) {
            itemsString[i] = ITEMS.get(i).toString();
        }
        return itemsString;
    }
}
