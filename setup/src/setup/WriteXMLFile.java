/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

/**
 *
 * @author gabesz
 */
import java.io.File;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXMLFile {

    public WriteXMLFile(String myFilePath, String path, Vector v) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("colors");
            doc.appendChild(rootElement);

            for (int i = 0; i < v.size(); i++) {
                //staff elements
                Element color = doc.createElement("color");
                rootElement.appendChild(color);

                Vector subV = (Vector) v.get(i);

                //firstname elements
                Element column = doc.createElement("column");
                column.appendChild(doc.createTextNode(subV.get(1).toString()));
                color.appendChild(column);

                //lastname elements
                Element match = doc.createElement("match");
                match.appendChild(doc.createTextNode(subV.get(2).toString()));
                color.appendChild(match);

                //nickname elements
                Element background = doc.createElement("background");
                background.appendChild(doc.createTextNode(subV.get(3).toString()));
                color.appendChild(background);

                //salary elements
                Element foreground = doc.createElement("foreground");
                foreground.appendChild(doc.createTextNode(subV.get(4).toString()));
                color.appendChild(foreground);
            }
            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            String separator = System.getProperty("file.separator");
            String dirPath = System.getProperty("user.dir") + separator + "logreaderxml";
            File dir = new File(dirPath);
            dir.mkdirs();
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

//            System.out.println("Done");


        } catch (ParserConfigurationException ex) {
            StackTraceElement[] STE = ex.getStackTrace();
            for (int i = STE.length - 1; i >= 0; i--) {
                if (STE[i].getClassName().equals(this.getClass().getName())) {
                    System.out.println(ex + "\t\tclass: " + STE[i].getClassName() + "\t\tline: " + STE[i].getLineNumber());
                }
            }
        } catch (TransformerException ex) {
            StackTraceElement[] STE = ex.getStackTrace();
            for (int i = STE.length - 1; i >= 0; i--) {
                if (STE[i].getClassName().equals(this.getClass().getName())) {
                    System.out.println(ex + "\t\tclass: " + STE[i].getClassName() + "\t\tline: " + STE[i].getLineNumber());
                }
            }
        }
    }
}
