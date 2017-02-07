/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

/**
 *
 * @author sabayon
 */
public class XML {

    protected String title = "UDP Server";
    protected String port = "8080";
    protected String date = "true";
    protected String maxRow = "500";
    protected String debug = "true";
    protected boolean browseBackToolTipText=false;
    protected boolean currentViewTooltipText=false;
    protected String defaultLogFile = "";
    protected String header_01 = "";
    protected String header_02 = "";
    protected String header_03 = "";
    protected String header_04 = "";
    protected String header_05 = "";
    protected String header_06 = "";
    protected String header_07 = "";
    protected String header_08 = "";
    protected String header_09 = "";
    protected String header_10 = "";
    protected String header_11 = "";
    protected String header_12 = "";
    protected String width_column_1 = "";
    protected String width_column_2 = "";
    protected String width_column_3 = "";
    protected String width_column_4 = "";
    protected String width_column_5 = "";
    protected String width_column_6 = "";
    protected String width_column_7 = "";
    protected String width_column_8 = "";
    protected String width_column_9 = "";
    protected String width_column_10 = "";
    protected String width_column_11 = "";
    protected String width_column_12 = "";
    protected String width_column[];
    protected String column_01_begin = "";
    protected String column_02_begin = "";
    protected String column_03_begin = "";
    protected String column_04_begin = "";
    protected String column_05_begin = "";
    protected String column_06_begin = "";
    protected String column_07_begin = "";
    protected String column_08_begin = "";
    protected String column_09_begin = "";
    protected String column_10_begin = "";
    protected String column_11_begin = "";
    protected String column_12_begin = "";
    protected String begin[];
    protected String column_01_end = "";
    protected String column_02_end = "";
    protected String column_03_end = "";
    protected String column_04_end = "";
    protected String column_05_end = "";
    protected String column_06_end = "";
    protected String column_07_end = "";
    protected String column_08_end = "";
    protected String column_09_end = "";
    protected String column_10_end = "";
    protected String column_11_end = "";
    protected String column_12_end = "";
    protected String end[];

    public void xml(String path) throws Exception {

        ReadXML readXML = new ReadXML();
        readXML.doReading(path);

        XMLParser xmlP = new XMLParser();
        String xmlString = readXML.getXmlString();
        this.setTitle(xmlP.getXMLTagValue(xmlString, "title", this.getTitle()));
        this.setPort(xmlP.getXMLTagValue(xmlString, "port", this.getPort()));
        this.setDate(xmlP.getXMLTagValue(xmlString, "date", this.getDate()));
        this.setMaxRow(xmlP.getXMLTagValue(xmlString, "maxrow", this.getMaxRow()));
        this.setDebug(xmlP.getXMLTagValue(xmlString, "debug", this.getDebug()));
        this.setBrowseBackToolTipText(xmlP.getXMLTagValue(xmlString, "browseBackToolTipText"));
        this.setCurrentViewTooltipText(xmlP.getXMLTagValue(xmlString, "currentViewToolTipText"));

        this.setHeader_01(xmlP.getXMLTagValue(xmlString, "header_01"));
        this.setHeader_02(xmlP.getXMLTagValue(xmlString, "header_02"));
        this.setHeader_03(xmlP.getXMLTagValue(xmlString, "header_03"));
        this.setHeader_04(xmlP.getXMLTagValue(xmlString, "header_04"));
        this.setHeader_05(xmlP.getXMLTagValue(xmlString, "header_05"));
        this.setHeader_06(xmlP.getXMLTagValue(xmlString, "header_06"));
        this.setHeader_07(xmlP.getXMLTagValue(xmlString, "header_07"));
        this.setHeader_08(xmlP.getXMLTagValue(xmlString, "header_08"));
        this.setHeader_09(xmlP.getXMLTagValue(xmlString, "header_09"));
        this.setHeader_10(xmlP.getXMLTagValue(xmlString, "header_10"));
        this.setHeader_11(xmlP.getXMLTagValue(xmlString, "header_11"));
        this.setHeader_12(xmlP.getXMLTagValue(xmlString, "header_12"));

        this.setWidth_column_1(xmlP.getXMLTagValue(xmlString, "width_column_1", "100"));
        this.setWidth_column_2(xmlP.getXMLTagValue(xmlString, "width_column_2", "100"));
        this.setWidth_column_3(xmlP.getXMLTagValue(xmlString, "width_column_3", "100"));
        this.setWidth_column_4(xmlP.getXMLTagValue(xmlString, "width_column_4", "100"));
        this.setWidth_column_5(xmlP.getXMLTagValue(xmlString, "width_column_5", "100"));
        this.setWidth_column_6(xmlP.getXMLTagValue(xmlString, "width_column_6", "100"));
        this.setWidth_column_7(xmlP.getXMLTagValue(xmlString, "width_column_7", "100"));
        this.setWidth_column_8(xmlP.getXMLTagValue(xmlString, "width_column_8", "100"));
        this.setWidth_column_9(xmlP.getXMLTagValue(xmlString, "width_column_9", "100"));
        this.setWidth_column_10(xmlP.getXMLTagValue(xmlString, "width_column_10", "100"));
        this.setWidth_column_11(xmlP.getXMLTagValue(xmlString, "width_column_11", "100"));
        this.setWidth_column_12(xmlP.getXMLTagValue(xmlString, "width_column_12", "100"));

        this.setWidth_column();

        this.setColumn_01_begin(xmlP.getXMLTagValue(xmlString, "column_01_begin"));
        this.setColumn_02_begin(xmlP.getXMLTagValue(xmlString, "column_02_begin"));
        this.setColumn_03_begin(xmlP.getXMLTagValue(xmlString, "column_03_begin"));
        this.setColumn_04_begin(xmlP.getXMLTagValue(xmlString, "column_04_begin"));
        this.setColumn_05_begin(xmlP.getXMLTagValue(xmlString, "column_05_begin"));
        this.setColumn_06_begin(xmlP.getXMLTagValue(xmlString, "column_06_begin"));
        this.setColumn_07_begin(xmlP.getXMLTagValue(xmlString, "column_07_begin"));
        this.setColumn_08_begin(xmlP.getXMLTagValue(xmlString, "column_08_begin"));
        this.setColumn_09_begin(xmlP.getXMLTagValue(xmlString, "column_09_begin"));
        this.setColumn_10_begin(xmlP.getXMLTagValue(xmlString, "column_10_begin"));
        this.setColumn_11_begin(xmlP.getXMLTagValue(xmlString, "column_11_begin"));
        this.setColumn_12_begin(xmlP.getXMLTagValue(xmlString, "column_12_begin"));

        this.setBegin();

        this.setColumn_01_end(xmlP.getXMLTagValue(xmlString, "column_01_end"));
        this.setColumn_02_end(xmlP.getXMLTagValue(xmlString, "column_02_end"));
        this.setColumn_03_end(xmlP.getXMLTagValue(xmlString, "column_03_end"));
        this.setColumn_04_end(xmlP.getXMLTagValue(xmlString, "column_04_end"));
        this.setColumn_05_end(xmlP.getXMLTagValue(xmlString, "column_05_end"));
        this.setColumn_06_end(xmlP.getXMLTagValue(xmlString, "column_06_end"));
        this.setColumn_07_end(xmlP.getXMLTagValue(xmlString, "column_07_end"));
        this.setColumn_08_end(xmlP.getXMLTagValue(xmlString, "column_08_end"));
        this.setColumn_09_end(xmlP.getXMLTagValue(xmlString, "column_09_end"));
        this.setColumn_10_end(xmlP.getXMLTagValue(xmlString, "column_10_end"));
        this.setColumn_11_end(xmlP.getXMLTagValue(xmlString, "column_11_end"));
        this.setColumn_12_end(xmlP.getXMLTagValue(xmlString, "column_12_end"));

        this.setEnd();
        String sep = System.getProperty("file.separator");
        this.setDefaultLogFile(System.getProperty("user.dir") + sep + "logs" + sep + this.getPort() + sep);
        this.setDefaultLogFile(xmlP.getXMLTagValue(xmlString, "log", this.getDefaultLogFile()));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(String maxRow) {
        this.maxRow = maxRow;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public boolean isBrowseBackToolTipText() {
        return browseBackToolTipText;
    }

    public void setBrowseBackToolTipText(String browseBackToolTipText) {
        if(browseBackToolTipText.toLowerCase().equals("true")){
            this.browseBackToolTipText = true;
        }else if(browseBackToolTipText.toLowerCase().equals("false")){
            this.browseBackToolTipText = false;
        }
    }

    public boolean isCurrentViewTooltipText() {
        return currentViewTooltipText;
    }

    public void setCurrentViewTooltipText(String currentViewTooltipText) {
        if(currentViewTooltipText.toLowerCase().equals("true")){
            this.currentViewTooltipText = true;
        }else if(currentViewTooltipText.toLowerCase().equals("false")){
            this.currentViewTooltipText = false;
        }
    }
    
    
    
    public String getColumn_01_begin() {
        return column_01_begin;
    }

    public void setColumn_01_begin(String column_01_begin) {
        this.column_01_begin = column_01_begin;
    }

    public String getColumn_01_end() {
        return column_01_end;
    }

    public void setColumn_01_end(String column_01_end) {
        this.column_01_end = column_01_end;
    }

    public String getColumn_02_begin() {
        return column_02_begin;
    }

    public void setColumn_02_begin(String column_02_begin) {
        this.column_02_begin = column_02_begin;
    }

    public String getColumn_02_end() {
        return column_02_end;
    }

    public void setColumn_02_end(String column_02_end) {
        this.column_02_end = column_02_end;
    }

    public String getColumn_03_begin() {
        return column_03_begin;
    }

    public void setColumn_03_begin(String column_03_begin) {
        this.column_03_begin = column_03_begin;
    }

    public String getColumn_03_end() {
        return column_03_end;
    }

    public void setColumn_03_end(String column_03_end) {
        this.column_03_end = column_03_end;
    }

    public String getColumn_04_begin() {
        return column_04_begin;
    }

    public void setColumn_04_begin(String column_04_begin) {
        this.column_04_begin = column_04_begin;
    }

    public String getColumn_04_end() {
        return column_04_end;
    }

    public void setColumn_04_end(String column_04_end) {
        this.column_04_end = column_04_end;
    }

    public String getColumn_05_begin() {
        return column_05_begin;
    }

    public void setColumn_05_begin(String column_05_begin) {
        this.column_05_begin = column_05_begin;
    }

    public String getColumn_05_end() {
        return column_05_end;
    }

    public void setColumn_05_end(String column_05_end) {
        this.column_05_end = column_05_end;
    }

    public String getColumn_06_begin() {
        return column_06_begin;
    }

    public void setColumn_06_begin(String column_06_begin) {
        this.column_06_begin = column_06_begin;
    }

    public String getColumn_06_end() {
        return column_06_end;
    }

    public void setColumn_06_end(String column_06_end) {
        this.column_06_end = column_06_end;
    }

    public String getColumn_07_begin() {
        return column_07_begin;
    }

    public void setColumn_07_begin(String column_07_begin) {
        this.column_07_begin = column_07_begin;
    }

    public String getColumn_07_end() {
        return column_07_end;
    }

    public void setColumn_07_end(String column_07_end) {
        this.column_07_end = column_07_end;
    }

    public String getColumn_08_begin() {
        return column_08_begin;
    }

    public void setColumn_08_begin(String column_08_begin) {
        this.column_08_begin = column_08_begin;
    }

    public String getColumn_08_end() {
        return column_08_end;
    }

    public void setColumn_08_end(String column_08_end) {
        this.column_08_end = column_08_end;
    }

    public String getColumn_09_begin() {
        return column_09_begin;
    }

    public void setColumn_09_begin(String column_09_begin) {
        this.column_09_begin = column_09_begin;
    }

    public String getColumn_09_end() {
        return column_09_end;
    }

    public void setColumn_09_end(String column_09_end) {
        this.column_09_end = column_09_end;
    }

    public String getColumn_10_begin() {
        return column_10_begin;
    }

    public void setColumn_10_begin(String column_10_begin) {
        this.column_10_begin = column_10_begin;
    }

    public String getColumn_10_end() {
        return column_10_end;
    }

    public void setColumn_10_end(String column_10_end) {
        this.column_10_end = column_10_end;
    }

    public String getColumn_11_begin() {
        return column_11_begin;
    }

    public void setColumn_11_begin(String column_11_begin) {
        this.column_11_begin = column_11_begin;
    }

    public String getColumn_11_end() {
        return column_11_end;
    }

    public void setColumn_11_end(String column_11_end) {
        this.column_11_end = column_11_end;
    }

    public String getColumn_12_begin() {
        return column_12_begin;
    }

    public void setColumn_12_begin(String column_12_begin) {
        this.column_12_begin = column_12_begin;
    }

    public String getColumn_12_end() {
        return column_12_end;
    }

    public void setColumn_12_end(String column_12_end) {
        this.column_12_end = column_12_end;
    }

    public String getHeader_01() {
        return header_01;
    }

    public void setHeader_01(String header_01) {
        this.header_01 = header_01;
    }

    public String getHeader_02() {
        return header_02;
    }

    public void setHeader_02(String header_02) {
        this.header_02 = header_02;
    }

    public String getHeader_03() {
        return header_03;
    }

    public void setHeader_03(String header_03) {
        this.header_03 = header_03;
    }

    public String getHeader_04() {
        return header_04;
    }

    public void setHeader_04(String header_04) {
        this.header_04 = header_04;
    }

    public String getHeader_05() {
        return header_05;
    }

    public void setHeader_05(String header_05) {
        this.header_05 = header_05;
    }

    public String getHeader_06() {
        return header_06;
    }

    public void setHeader_06(String header_06) {
        this.header_06 = header_06;
    }

    public String getHeader_07() {
        return header_07;
    }

    public void setHeader_07(String header_07) {
        this.header_07 = header_07;
    }

    public String getHeader_08() {
        return header_08;
    }

    public void setHeader_08(String header_08) {
        this.header_08 = header_08;
    }

    public String getHeader_09() {
        return header_09;
    }

    public void setHeader_09(String header_09) {
        this.header_09 = header_09;
    }

    public String getHeader_10() {
        return header_10;
    }

    public void setHeader_10(String header_10) {
        this.header_10 = header_10;
    }

    public String getHeader_11() {
        return header_11;
    }

    public void setHeader_11(String header_11) {
        this.header_11 = header_11;
    }

    public String getHeader_12() {
        return header_12;
    }

    public void setHeader_12(String header_12) {
        this.header_12 = header_12;
    }

    public String getWidth_column_1() {
        return width_column_1;
    }

    public void setWidth_column_1(String width_column_1) {
        this.width_column_1 = width_column_1;
    }

    public String getWidth_column_10() {
        return width_column_10;
    }

    public void setWidth_column_10(String width_column_10) {
        this.width_column_10 = width_column_10;
    }

    public String getWidth_column_11() {
        return width_column_11;
    }

    public void setWidth_column_11(String width_column_11) {
        this.width_column_11 = width_column_11;
    }

    public String getWidth_column_12() {
        return width_column_12;
    }

    public void setWidth_column_12(String width_column_12) {
        this.width_column_12 = width_column_12;
    }

    public String getWidth_column_2() {
        return width_column_2;
    }

    public void setWidth_column_2(String width_column_2) {
        this.width_column_2 = width_column_2;
    }

    public String getWidth_column_3() {
        return width_column_3;
    }

    public void setWidth_column_3(String width_column_3) {
        this.width_column_3 = width_column_3;
    }

    public String getWidth_column_4() {
        return width_column_4;
    }

    public void setWidth_column_4(String width_column_4) {
        this.width_column_4 = width_column_4;
    }

    public String getWidth_column_5() {
        return width_column_5;
    }

    public void setWidth_column_5(String width_column_5) {
        this.width_column_5 = width_column_5;
    }

    public String getWidth_column_6() {
        return width_column_6;
    }

    public void setWidth_column_6(String width_column_6) {
        this.width_column_6 = width_column_6;
    }

    public String getWidth_column_7() {
        return width_column_7;
    }

    public void setWidth_column_7(String width_column_7) {
        this.width_column_7 = width_column_7;
    }

    public String getWidth_column_8() {
        return width_column_8;
    }

    public void setWidth_column_8(String width_column_8) {
        this.width_column_8 = width_column_8;
    }

    public String getWidth_column_9() {
        return width_column_9;
    }

    public void setWidth_column_9(String width_column_9) {
        this.width_column_9 = width_column_9;
    }

    public String[] getWidth_column() {
        return width_column;
    }

    public void setWidth_column() {
        String[] width_column_ = {
            this.getWidth_column_1(),
            this.getWidth_column_2(),
            this.getWidth_column_3(),
            this.getWidth_column_4(),
            this.getWidth_column_5(),
            this.getWidth_column_6(),
            this.getWidth_column_7(),
            this.getWidth_column_8(),
            this.getWidth_column_9(),
            this.getWidth_column_10(),
            this.getWidth_column_11(),
            this.getWidth_column_12(),};
        this.width_column = width_column_;
    }

    public String[] getBegin() {
        return begin;
    }

    public void setBegin() {
        String[] beginString = {
            this.getColumn_01_begin(),
            this.getColumn_02_begin(),
            this.getColumn_03_begin(),
            this.getColumn_04_begin(),
            this.getColumn_05_begin(),
            this.getColumn_06_begin(),
            this.getColumn_07_begin(),
            this.getColumn_08_begin(),
            this.getColumn_09_begin(),
            this.getColumn_10_begin(),
            this.getColumn_11_begin(),
            this.getColumn_12_begin(),};
        this.begin = beginString;
    }

    public String[] getEnd() {
        return end;
    }

    public void setEnd() {
        String[] endString = {
            this.getColumn_01_end(),
            this.getColumn_02_end(),
            this.getColumn_03_end(),
            this.getColumn_04_end(),
            this.getColumn_05_end(),
            this.getColumn_06_end(),
            this.getColumn_07_end(),
            this.getColumn_08_end(),
            this.getColumn_09_end(),
            this.getColumn_10_end(),
            this.getColumn_11_end(),
            this.getColumn_12_end(),};
        this.end = endString;
    }

    public String getDefaultLogFile() {
        return defaultLogFile;
    }

    public void setDefaultLogFile(String defaultLogFile) {
        this.defaultLogFile = defaultLogFile;
    }
}
