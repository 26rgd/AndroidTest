package cn.com.grentech.specialcar.common.unit;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/28.
 */

public class DateUnit {
    private static String tag=DateUnit.class.getName();
    public static String formatDate(long i, String format) {
        String time = "";
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date d = new Date(i);
        time = sf.format(d);
        return time;
    }
    public static Date StringToTimeDate(String s, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            Date d = sf.parse(s);
            return d;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            ErrorUnit.println(tag,e);
            e.printStackTrace();
            return new Date();
        }

    }
}
