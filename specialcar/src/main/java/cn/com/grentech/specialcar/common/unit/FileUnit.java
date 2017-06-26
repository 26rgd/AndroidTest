package cn.com.grentech.specialcar.common.unit;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/5/9.
 */

public class FileUnit {
   private static String tag=FileUnit.class.getName();
    private static String fileTemps = null;
    private static String fileFiles = null;

    public static void iniDir(Context context) {
        File fileTemp = context.getExternalCacheDir();
        File fileFile = context.getExternalFilesDir(null);
        if (!fileFile.exists())
            fileFile.mkdirs();
        if (!fileTemp.exists())
            fileTemp.mkdirs();
        fileTemps = fileTemp.getAbsolutePath();
        fileFiles = fileFile.getAbsolutePath();

    }

    public static void writeAppDataFile(Context context, String filename, String data, int mode) {
        try {
            File file = new File(fileFiles, filename);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            byte[] bytes = data.getBytes("UTF-8");
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (Exception e) {
            ErrorUnit.println(tag,e);
        }
    }

    public static void writeAppTempFile(Context context, String filename, String data, int mode) {
        try {
            File file = new File(fileTemps, filename);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            byte[] bytes = data.getBytes("UTF-8");
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (Exception e) {
            ErrorUnit.println(tag,e);
        }
    }

    public static void writeAppLogFile(Context context, String filename, String data, int mode) {
        try {

            String day = DateUnit.formatDate(System.currentTimeMillis(), "yyyyMMddHH");
            File file = new File(fileTemps, day + filename);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            byte[] bytes = data.getBytes("UTF-8");
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (Exception e) {
            ErrorUnit.println(tag,e);
        }
    }

    public static void saveSeriallizable(String filename, Serializable s) {
        try {
            File file = new File(fileFiles, filename);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(s);
        } catch (Exception e) {
            ErrorUnit.println(tag,e);
        }
    }

    public static Object readSeriallizable(String filename) {
        try {
            File file = new File(fileFiles, filename);
            if (!file.exists())
                return null;
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ios = new ObjectInputStream(fileInputStream);
            Object result = ios.readObject();
            return result;
        } catch (Exception e) {

            ErrorUnit.println(tag,e);
            return null;
        }
    }
}
