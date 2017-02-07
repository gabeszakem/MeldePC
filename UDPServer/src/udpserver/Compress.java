/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import logreader.DeleteDir;
import java.io.File;

/**
 * A fájlokat és a könyvtárakat helymegtakarítás véget tömörítjük
 * for slave free place compress files
 * @author gabeszakem
 */
public class Compress {

    /**
     * 
     * @throws Exception 
     */
    public static void compress() throws Exception {
        String filePath = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "logs"
                + System.getProperty("file.separator");
        compress(filePath);
    }

    /**
     * 
     * @param filePath
     * @throws Exception 
     */
    public static void compress(String filePath) throws Exception {
        try {
            File inFolder = new File(filePath);

            if (inFolder.isDirectory()) {
                String files[] = inFolder.list();
                String actDate = Tools.actualDate("yyyy_MM").toString();
                /*
                 * A könyvtárban az összes fájlt megkeressük files[] tömb
                 * tartalmazza a könyvtárban található összes filet, és könyvtárat
                 */
                for (int i = 0; i < files.length; i++) {
                    File folder = new File(filePath + files[i]);
                    String filesInFolder[] = folder.list();
                    String actDay = Tools.actualDate("yyyy_MM_dd").toString();
                    for (int j = 0; j < filesInFolder.length; j++) {
                        String fileName[] = filesInFolder[j].split("\\.");
                        if (!fileName[fileName.length - 1].equals("zip")) {
                            if (!fileName[0].equals(actDay)) {
                                boolean zip = FolderZiper.zipFile(filePath + files[i] + System.getProperty("file.separator") + filesInFolder[j]);
                                if (zip) {
                                    boolean deleteFile = DeleteDir.removeFile(filePath + files[i] + System.getProperty("file.separator") + filesInFolder[j]);
                                    if (deleteFile) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.err.println(e);
        }
    }
}
