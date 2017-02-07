/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader.xml;

/**
 *
 * @author gabesz
 */
import java.io.File;
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

    public WriteXMLFile(String myFilePath,String path) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("setup");
            doc.appendChild(rootElement);


            //firstname elements
            Element filePath = doc.createElement("filepath");
            filePath.appendChild(doc.createTextNode(myFilePath));
            rootElement.appendChild(filePath);

            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            String separator = System.getProperty("file.separator");
            String dirPath = System.getProperty("user.dir") + separator + "logreaderxml";
            //String path = dirPath + separator+"setup.xml";
            File dir =new File(dirPath);
            dir.mkdirs();
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

            //System.out.println("Done");

        } catch (ParserConfigurationException ex) {
            StackTraceElement[] STE = ex.getStackTrace();
            for (int i = STE.length - 1; i >= 0; i--) {
                if (STE[i].getClassName().equals(this.getClass().getName())) {
                    System.out.println(ex+"\t\tclass: "+STE[i].getClassName() + "\t\tline: " + STE[i].getLineNumber());
                }
            }
        } catch (TransformerException ex) {
            StackTraceElement[] STE = ex.getStackTrace();
            for (int i = STE.length - 1; i >= 0; i--) {
                if (STE[i].getClassName().equals(this.getClass().getName())) {
                    System.out.println(ex+"\t\tclass: "+STE[i].getClassName() + "\t\tline: " + STE[i].getLineNumber());
                }
            }
        }
    }
}
