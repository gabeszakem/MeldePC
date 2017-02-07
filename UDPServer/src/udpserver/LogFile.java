/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package udpserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author gabeszakem
 */
public class LogFile {
    protected static String defaultLogFile = System.getProperty("user.dir")+
            System.getProperty("file.separator")+"logs"+
            System.getProperty("file.separator");
    protected static String defaultExt = "csv";
    public static void write(String text) throws IOException {
         write(text,defaultExt);
     }
    public static void write(String filePath, String text) throws IOException {
         write(filePath, text,defaultExt);
     }
    public static void write(String filePath, String text,String ext) throws IOException {
         TimeZone tz = TimeZone.getTimeZone("EST"); // or PST, MID, etc ...
         Date now = new Date();
         DateFormat df = new SimpleDateFormat ("yyyy.MM.dd\thh:mm:ss ");
         DateFormat year = new SimpleDateFormat ("yyyy");
         DateFormat month = new SimpleDateFormat ("MM");
         DateFormat day = new SimpleDateFormat ("dd");
         df.setTimeZone(tz);
         //String currentTime = df.format(now);

         //String myFile = f+year.format(now)+"_"+month.format(now)+"_"+day.format(now)+".csv";
		 filePath=filePath+year.format(now)+"_"+month.format(now)+System.getProperty("file.separator");
         String myFile = filePath+year.format(now)+"_"+month.format(now)+"_"+day.format(now)+"."+ext;
         try{
             boolean success = (new File(filePath)).mkdirs();
             if (success) {
                //System.out.println("Directory: " + filePath + " created");
             }
         }catch(Exception e){
                System.err.println(e);
         }
         //System.out.println(myFile);
         File file =  new File(myFile);
         file.setWritable(true);
         FileWriter aWriter = new FileWriter(myFile, true);
         //aWriter.write(currentTime + " ; " + s + "\n");
         aWriter.write(text+ "\n");
         aWriter.flush();
         aWriter.close();
         //Hogy mások ne tudják irni az excelt!!!!
         file.setReadOnly();
     }
}

