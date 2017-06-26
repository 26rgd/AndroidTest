package cn.com.grentech.specialcar.common.unit;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.grentech.specialcar.SysApplication;

import static android.R.attr.path;

/**
 * Created by Administrator on 2017/3/23.
 */

public class StringUnit {
    public static boolean isCheckNumber(String src) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(src);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    //    private static int getAsscii(String s)throws Exception
//    {
//        if(s==null || s.length()!=1){
//        throw new Exception("自定义异常");
//    }
//        return s.getBytes()[0];
//    }
    public static String getUrlTransfer(String s) {
        String result = "";
        byte[] buf = s.getBytes();
        for (byte b : buf) {
            String per = "%" + Integer.toHexString(b + 256);
            if (b < 0) {
                per = "%" + Integer.toHexString(b);
            }
            result = result + per;
        }
        return result;
    }

    public static void println(String className,String s) {
        String data=DateUnit.formatDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "  "+className+" | " + s+"\r\n";
        System.out.println(data);
        FileUnit.writeAppLogFile(SysApplication.getInstance().getContext(),"log.txt",data, Context.MODE_APPEND);
    }
//    public static void println(  String s) {
//        System.out.println(DateUnit.formatDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "  "+" | " + s);
//    }
    /**
     * 字符串补齐
     * <p>
     * param type
     * 0表示左补齐 1右补齐
     * param src
     * param suppChar
     * 补的字符
     * param totallength
     * 补成后的长度
     * return
     */
    public static String supplement(boolean left, String src, String suppChar, int totallength) {
        if (src.length() >= totallength)
            return src;
        for (int i = src.length(); i < totallength; i++) {
            if (left == true) {
                src = suppChar + src;
            } else {
                src = src + suppChar;
            }
        }

        return src;
    }


    public static boolean isEmpty(String s) {
        if (s == null || s.length() ==0)
            return true;
        return false;
    }


    public static String getFileName(String urlpath) {
        String filename = "";
        if (!StringUnit.isEmpty(urlpath)) {
            int i = urlpath.lastIndexOf("/") + 1;
            filename = urlpath.substring(i);
        }
        return filename;
    }

}
