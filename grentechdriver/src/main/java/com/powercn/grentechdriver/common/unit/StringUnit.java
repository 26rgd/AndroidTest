package com.powercn.grentechdriver.common.unit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void println(String s) {
        System.out.println(DateUnit.formatDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "  " + s);
    }

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
}
