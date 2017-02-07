/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FolderZiper {

    static public boolean zipFolder(String srcFolder) throws Exception {
        String defaultDestZipFile = srcFolder + ".zip";
        return zipFolder(srcFolder, defaultDestZipFile);
    }

    static public boolean zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip("", srcFolder, zip);
        zip.flush();
        zip.close();
        return true;
    }

    static public boolean zipFile(String srcFile) throws Exception {
        String fileName[] = srcFile.split("\\.");
        String defaultDestZipFile = "";
        for (int i = 0; i < fileName.length - 1; i++) {
            defaultDestZipFile += fileName[i];
        }
        defaultDestZipFile += ".zip";
        //System.out.println("Default zip name: "+ defaultDestZipFile);
        return zipFile(srcFile, defaultDestZipFile);
    }

    static public boolean zipFile(String srcFile, String destZipFile) throws Exception {
        File folder = new File(srcFile);
        folder.setWritable(true);
        ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destZipFile)));
        byte[] data = new byte[1024];
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(srcFile));
        int count;
        zip.putNextEntry(new ZipEntry("/" + folder.getName()));
        while ((count = in.read(data, 0, 1000)) != -1) {
            zip.write(data, 0, count);
        }
        in.close();
        zip.flush();
        zip.close();
        return true;
    }

    static private void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
            //be kell zárni a megnyitott fájlokat!!!!!
            in.close();
        }
    }

    static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }

    }
}
