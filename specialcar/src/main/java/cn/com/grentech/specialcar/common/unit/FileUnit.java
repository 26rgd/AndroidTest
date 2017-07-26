package cn.com.grentech.specialcar.common.unit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.attr.path;
import static android.R.id.list;

/**
 * Created by Administrator on 2017/5/9.
 */

public class FileUnit {
    private static String tag = FileUnit.class.getName();
    private  static String fileTemps = null;
    private static String fileFiles = null;

    public static String getTempPath()
    {
        return fileTemps;
    }

    public static String getFilePath()
    {
        return fileFiles;
    }

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
            ErrorUnit.println(tag, e);
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
            ErrorUnit.println(tag, e);
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
            ErrorUnit.println(tag, e);
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
            ErrorUnit.println(tag, e);
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

            ErrorUnit.println(tag, e);
            return null;
        }
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {

        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            pd.setOnDismissListener(null);
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    public static List<String> searchFiles(String path) {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (File line : files) {
            if (line.isDirectory()) {
            } else {
                list.add(line.getAbsolutePath());
            }
        }
        return list;
    }

    public static List<String> searchLogFiles() {
        String path=fileTemps;
        List<String> list =searchFiles(fileTemps);
        return list;
    }
}
