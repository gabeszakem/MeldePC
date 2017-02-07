/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author gkovacs02
 */
public class UDPMain {

    public static UDPServer[] us = null;

    public static UDPServer[] udpMain() throws Exception {
        String separator = System.getProperty("file.separator");
        String path = System.getProperty("user.dir") + separator + "xml" + separator;
        System.out.println(Tools.actualDate("yyyy_MM_dd HH:mm:ss:SS").toString()
                + " path: "
                + path);

        ArrayList files = new ArrayList();
        try {
            File inFolder = new File(path);
            if (inFolder.isDirectory()) {
                String filesString[] = inFolder.list();
                for (int i = 0; i < filesString.length; i++) {
                    System.out.println(filesString[i]);
                    String fileName[] = filesString[i].split("\\.");
                    if (fileName.length > 0) {
                        if (fileName[fileName.length - 1].equals("xml")) {
                            files.add(filesString[i]);
                            System.out.println(filesString[i]);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
        if (!files.isEmpty()) {
            us = new UDPServer[files.size()];
            for (int i = 0; i < files.size(); i++) {
                us[i] = new UDPServer(path + files.get(i).toString());
            }
        }
        return us;
    }
}
