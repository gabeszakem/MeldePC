/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package udpserver;

//import java.util.Vector;

/**
 *
 * @author gkovacs02
 */
public class XMLParser
{
    /**
     * Pass only the name of the section for example "QUESTION"
     */
    public String getXMLTagValue(String xml, String section) throws Exception
    {
        String defaultString="";
        return getXMLTagValue(xml, section,defaultString);
    }


    public String getXMLTagValue(String xml, String section,String defaultString) throws Exception
    {
        String xmlString = xml;
        //System.out.println(xmlString);
        String v = new String();
        //Vector v = new Vector();
        String beginTagToSearch = "<" + section + ">";
        String endTagToSearch = "</" + section + ">";
        //System.out.println("search: "+ beginTagToSearch + "-->" + endTagToSearch);

        // Look for the first occurrence of begin tag
        int index = xmlString.indexOf(beginTagToSearch);
        //System.out.println("index: "+index);


        while(index != -1)
        {
                             // Look for end tag
            // DOES NOT HANDLE
            int lastIndex = xmlString.indexOf(endTagToSearch);


            // Make sure there is no error
            if((lastIndex == -1) || (lastIndex < index))
                throw new Exception("Parse Error");

            // extract the substring
            String subs = xmlString.substring((index + beginTagToSearch.length()), lastIndex) ;

                            // Add it to our list of tag values
            //v.addElement(subs);
            v=subs;
                            // Try it again. Narrow down to the part of string which is not
                            // processed yet.
            try
            {
                xmlString = xmlString.substring(lastIndex + endTagToSearch.length());
            }
            catch(Exception e)
            {
                xmlString =defaultString;
            }

                             // Start over again by searching the first occurrence of the begin tag
                             // to continue the loop.

            index = xmlString.indexOf(beginTagToSearch);
        }
        if(v == null ? "" == null : v.equals("")){
            v=defaultString;
        }
        return v;

    }
}


