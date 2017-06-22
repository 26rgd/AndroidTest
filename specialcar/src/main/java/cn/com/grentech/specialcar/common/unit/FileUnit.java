package cn.com.grentech.specialcar.common.unit;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/5/9.
 */

public class FileUnit {

    public static void writeAppDataFile(Context context,String filename, String data, int mode)
    {
       try {
           FileOutputStream fileOutputStream =context.openFileOutput(filename,mode);
           byte [] bytes=data.getBytes("UTF-8");
           fileOutputStream.write(bytes);
           fileOutputStream.close();
       }catch (Exception e){
           e.printStackTrace();
       }
    }

}
