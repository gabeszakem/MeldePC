/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

/**
 *
 * @author gabesz
 */
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DriveFaultWritter {

    public DriveFaultWritter() {

        String separator = System.getProperty("file.separator");
        String pathDir = System.getProperty("user.dir") + separator + "logreaderxml";
        String myFilePath = pathDir + separator + "drivesfaults.xml";

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("faults");
            doc.appendChild(rootElement);

            for(int i=1000; i<1010;i++){
            //firstname elements
            String element="F"+Integer.toString(i);
            Element filePath = doc.createElement(element);
            filePath.appendChild(doc.createTextNode(myFilePath));
            rootElement.appendChild(filePath);
            }

            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            String dirPath = pathDir;
            //String path = dirPath + separator+"setup.xml";
            File dir = new File(dirPath);
            dir.mkdirs();
            StreamResult result = new StreamResult(new File(myFilePath));
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
