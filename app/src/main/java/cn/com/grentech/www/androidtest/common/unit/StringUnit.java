package cn.com.grentech.www.androidtest.common.unit;

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


}
