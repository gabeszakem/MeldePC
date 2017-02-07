/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author gabeszakem
 */
public class UnZip {

    List<String> fileList;
    private static float job;
    private static long workTime;
    
    @SuppressWarnings("CallToThreadDumpStack")
    public static void unZipIt(String zipFile, String outputFolder) {
        long startTime = System.currentTimeMillis();
        byte[] buffer = new byte[1024];

        try {
            //create output directory is not exists
            float size = 0;
            setWorkTime(0L);
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            ZipFile zipF = new ZipFile(zipFile);
            Enumeration e = zipF.entries();
            while (e.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) e.nextElement();
                String entryName = entry.getName();
                long originalSize = entry.getSize();
                long compressedSize = entry.getCompressedSize();

                //System.out.print(entryName);
                size += (float) originalSize;
            }
            zipF.close();
            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            float partSize = 0;
            float persent = 0;
            long closeStartTime = 0;
            long closeStopTime = 0;
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                //System.out.println("file unzip : " + newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                BufferedOutputStream dest = new BufferedOutputStream(fos,
                        buffer.length);
                int len;
                while ((len = zis.read(buffer, 0,
                        buffer.length)) != -1) {
                    dest.write(buffer, 0, len);
                    partSize += len;
                    persent = ((partSize) / size) * 100;
                    setJob(persent);
                    
                }
                long stopTime = System.currentTimeMillis();
                setWorkTime(stopTime - startTime);
                closeStartTime=System.currentTimeMillis();
                dest.close();
                closeStopTime=System.currentTimeMillis();
                ze = zis.getNextEntry();
                
            }
            zis.closeEntry();
            zis.close();
            //System.out.println("Done: " + getWorkTime() + " : " + (closeStopTime - closeStartTime));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static float getJob() {
        return job;
    }

    private static void setJob(float job) {
        UnZip.job = job;
    }

    public static long getWorkTime() {
        return workTime;
    }

    private static void setWorkTime(long workTime) {
        UnZip.workTime = workTime;
    }


}
