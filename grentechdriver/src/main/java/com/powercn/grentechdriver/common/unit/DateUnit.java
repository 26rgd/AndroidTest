package com.powercn.grentechdriver.common.unit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/28.
 */

public class DateUnit {
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
            e.printStackTrace();
            return new Date();
        }

    }

    public static String getDisplayNow()
    {
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int week=calendar.get(Calendar.DAY_OF_WEEK);
        String mWay="";
        if(1==week){
            mWay ="天";
        }else if(2==week){
            mWay ="一";
        }else if(3==week){
            mWay ="二";
        }else if(4==week){
            mWay ="三";
        }else if(5==week){
            mWay ="四";
        }else if(6==week){
            mWay ="五";
        }else if(7==week){
            mWay ="六";
        }


            return StringUnit.supplement(true,month+"","0",2)+"月"+StringUnit.supplement(true,day+"","0",2)+"日  星期"+mWay;


    }
}
