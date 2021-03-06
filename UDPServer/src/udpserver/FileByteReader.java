/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package udpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author gabeszakem
 */
public class FileByteReader {
    public byte[] getBytesFromFile(String sPath) throws IOException {
        File file=new File(sPath);
        InputStream is = new FileInputStream(file);
        boolean debug = false;
        // Get the size of the file
        long length = file.length();
        if(debug==true){
            //System.out.println("Debug: lenght: "+length);
        }

        if (length > Integer.MAX_VALUE) {
            // File is too large
            if(debug==true){
                //System.out.println("Debug: File is too large");
            }
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            if(debug==true){
                System.out.println("Debug: numread: "+numRead);
            }
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
