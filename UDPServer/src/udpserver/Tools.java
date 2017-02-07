/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Tools collection
 * @author gabeszakem
 */
public class Tools {
    /**
     * get actual date
     * @param Dateform
     * @return 
     */
    public static String actualDate(String Dateform) {
        SimpleDateFormat formatter = new SimpleDateFormat(Dateform);
        Date now = new Date();
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTime(now);
        return formatter.format(calendar.getTime());
    }
    /**
     * Get hex from string
     * @param arg
     * @return 
     */
    public static String toHex(String arg) {
        try {
            return String.format("%x", new BigInteger(arg.getBytes("ISO8859_2")));
        } catch (Exception Ex) {
            return "";
        }
    }
    /**
     * Bayte[] to string with ISO8859_2 character code
     * @param b
     * @return 
     */
    public static String convert(byte[] b) {
        String newString;
        try {
            newString = new String(b, "ISO8859_2");
        } catch (Exception e) {
            newString = b.toString();
            System.err.println(e);
        }
        return newString;
    }
}
