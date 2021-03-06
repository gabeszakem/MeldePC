/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

/**
 *
 * @author gkovacs02
 */
import java.io.*;
import java.io.File;

public class ReadXML {

    /** XML String storage */
    private String xmlstring = "<?xml version=1.0 encoding=UTF-8?><title>UDP szerver PLC</title><port>1090</port><date>false</date><maxrow>1000</maxrow><debug>true</debug><header_01>DÁTUM</header_01><header_02>IDÕ</header_02><header_03>PRA</header_03><header_04>SUF</header_04><header_05>TYP</header_05><header_06>Num.</header_06><header_07>PARAMÉTER</header_07><header_08>MELDETEXT</header_08><header_09>KL.</header_09><header_10>BER</header_10><header_11>H</header_11><header_12>LFE-K</header_12><width_column_1>60</width_column_1><width_column_2>80</width_column_2><width_column_3>40</width_column_3><width_column_4>30</width_column_4><width_column_5>30</width_column_5><width_column_6>30</width_column_6><width_column_7>100</width_column_7><width_column_8>387</width_column_8><width_column_9>60</width_column_9><width_column_10>150</width_column_10><width_column_11>30</width_column_11><width_column_12>30</width_column_12><column_01_begin>2</column_01_begin><column_01_end>10</column_01_end><column_02_begin>13</column_02_begin><column_02_end>24</column_02_end><column_03_begin>28</column_03_begin><column_03_end>33</column_03_end><column_04_begin>36</column_04_begin><column_04_end>38</column_04_end><column_05_begin>45</column_05_begin><column_05_end>46</column_05_end><column_06_begin>48</column_06_begin><column_06_end>55</column_06_end><column_07_begin>56</column_07_begin><column_07_end>70</column_07_end><column_08_begin>76</column_08_begin><column_08_end>@</column_08_end><column_09_begin></column_09_begin><column_09_end></column_09_end><column_10_begin></column_10_begin><column_10_end></column_10_end><column_11_begin></column_11_begin><column_11_end></column_11_end><column_12_begin></column_12_begin><column_12_end></column_12_end>";

    /** Creates a new instance of ReadXml */
    public ReadXML() {
    }

    /**
     *
     * @return Xml String
     *
     */
    public String getXmlString() {
        return this.xmlstring;
    }

    /**
     * Reads file with help of the BufferedReader class into a StringBuffer
     * and returns it into an String
     * @param s String with the path to the file
     * @throws FileNotFoundException if file not found error is returned. please not, in the unit test expections are not covered
     * @throws IOException for all other file access errors
     */
    public void doReading(String s) {

        StringBuilder sb = new StringBuilder();
        String line;
        FileReader fread = null;
        File file = new File(s);
        try {
            fread = new FileReader(s);
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "8859_1"));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException ex) { // exception handlling
            StackTraceElement[] STE = ex.getStackTrace();
            for (int i = STE.length - 1; i >= 0; i--) {
                if (STE[i].getClassName().equals(this.getClass().getName())) {
                    System.out.println(ex+"\t\tclass: "+STE[i].getClassName() + "\t\tline: " + STE[i].getLineNumber());
                }
            }
        } catch (IOException ex) {
            StackTraceElement[] STE = ex.getStackTrace();
            for (int i = STE.length - 1; i >= 0; i--) {
                if (STE[i].getClassName().equals(this.getClass().getName())) {
                    System.out.println(ex+"\t\tclass: "+STE[i].getClassName() + "\t\tline: " + STE[i].getLineNumber());
                }
            }
        }
        this.xmlstring = sb.toString(); // set instance variable xmlstring
    }
}
