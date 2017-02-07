/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import java.io.File;

/**
 *
 * @author gabeszakem
 */
public class DeleteDir {

    public static boolean removeDirectory(String directory) throws Exception {
        File folder = new File(directory);
        return removeDirectory(folder);
    }

    public static boolean removeDirectory(File directory) throws Exception {

        //System.out.println("removeDirectory " + directory);

        if (directory == null) {
            return false;
        }
        if (!directory.exists()) {
            return true;
        }
        if (!directory.isDirectory()) {
            return false;
        }

        boolean disableDelete = true;

        String[] list = directory.list();

        // Some JVMs return null for File.list() when the
        // directory is empty.
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File entry = new File(directory, list[i]);

                //System.out.println("\tremoving entry " + entry);

                if (entry.isDirectory()) {
                    if (!removeDirectory(entry)) {
                        disableDelete = false;
                    }
                }
                if (entry.isFile()) {
                    if (!entry.exists()) {
                        System.err.println("\t\tentry is not exists");
                    }
                    if (!entry.canWrite()) {
                        System.err.println("\t\tentry is write protected");
                        if (entry.setWritable(true)) {
                            System.err.println("\t\tentry is set writable");
                        }
                    }
                    if (!entry.isAbsolute()) {
                        System.err.println("\t\tentry is not absolute");
                    }

                    //System.out.println("\t\tentry is file");
                    boolean success = entry.delete();
                    if (!success) {
                        disableDelete = false;
                        System.err.println("\t\t\tcan't delete entry");
                    }
                }
            }
            if (disableDelete == false) {
                return false;
            }
        }
        return directory.delete();
    }

    public static boolean removeFile(String fileString) throws Exception {
        File file = new File(fileString);
        return removeFile(file);
    }

    public static boolean removeFile(File file) throws Exception {
        boolean disableDelete = true;
        //System.out.println("\tremoving file " + file);

        if (file.isDirectory()) {
            if (!removeDirectory(file)) {
                disableDelete = false;
            }
        }
        if (file.isFile()) {
            if (!file.exists()) {
                System.err.println("\t\tfile is not exists");
                disableDelete = false;
            }
            if (!file.canWrite()) {
                System.err.println("\t\tfile is write protected");
                if (file.setWritable(true)) {
                    System.err.println("\t\tfile is set writable");
                }
            }
            if (!file.isAbsolute()) {
                System.err.println("\t\tfile is not absolute");
            }
            boolean success = file.delete();
            if (!success) {
                disableDelete = false;
                System.err.println("\t\t\tcan't delete file");
            }
        }
        if (disableDelete == false) {
            return false;
        } else {
            return true;
        }
    }
}
