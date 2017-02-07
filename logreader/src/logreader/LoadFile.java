/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;

/**
 *
 * @author gabeszakem
 */
public class LoadFile {

    private static Vector v;
    private static boolean newVector;
    private static int maxLine = 65536;
    private static String lastPath = "";
    private static boolean more = false;
    private static float job;
    private static Colorize c;

    public static void load(String path, int idx, String filter) {
        c = new Colorize();
        String[] line = null;
        setLastPath(path);
        v = new Vector();
        if (idx > 0) {
            try {
                File file = new File(path);
                //System.out.println("read: " + path);
                BufferedReader csvFile = new BufferedReader(new InputStreamReader(new FileInputStream(file), "8859_1"));
                String csvFileLine = "";
                int rowcount = 1;
                setJob(0);
                //go through lines
                while ((csvFileLine = csvFile.readLine()) != null /*& (rowcount < idx * maxLine)*/) {
                    line = csvFileLine.split("\\;");
                    /**
                     * Az üres sornál kiakadt a program
                     */
                    if (filter.equals("")) {
                        if (line.length > 1) {
                            if (((idx - 1) * maxLine <= rowcount) & (rowcount < idx * maxLine)) {
                                Vector subV = new Vector();
                                subV.addAll(Arrays.asList(line));
                                v.add(colorise(subV));
                                //v.add(subV);
                                setJob((float) (((float) rowcount / (float) (idx * maxLine)) * 100));
                            } else {
                                if (!(rowcount < idx * maxLine)) {
                                    break;
                                }
                            }
                        }
                        rowcount += 1;
                    } else {
                        String word = "";
                        for (int i = 0; i < line.length; i++) {
                            word += line[i].toString().toLowerCase();
                        }
                        if (word.matches("(?i).*" + filter + ".*")) {
                            Vector subV = new Vector();
                            //subV.add(rowcount);
                            subV.addAll(Arrays.asList(line));
                            //v.add(subV);
                            v.add(colorise(subV));
                            setJob((float) (((float) rowcount / (float) (idx * maxLine)) * 100));
                            rowcount += 1;
                        }
                    }
                }
                if (idx * maxLine <= rowcount) {
                    setMore(true);
                } else {
                    setMore(false);
                }
                csvFile.close();
                //System.out.println("read: done");
                setNewVector(true);
            } catch (Exception e) {
                //System.err.println("loadfile 53:" + e);
                setNewVector(false);
            }
        }
    }

    public static Vector getV() {
        return v;
    }

    public static void setV(Vector v) {
        LoadFile.v = v;
    }

    public static boolean isNewVector() {
        return newVector;
    }

    public static void setNewVector(boolean newVector) {
        LoadFile.newVector = newVector;
    }

    public static String getLastPath() {
        return lastPath;
    }

    public static void setLastPath(String lastPath) {
        LoadFile.lastPath = lastPath;
    }

    public static boolean isMore() {
        return more;
    }

    public static void setMore(boolean more) {
        LoadFile.more = more;
    }

    public static float getJob() {
        return job;
    }

    private static void setJob(float job) {
        LoadFile.job = job;
    }

    private static Vector colorise(Vector subv) {
        return c.calculateColors(subv);
    }

}
