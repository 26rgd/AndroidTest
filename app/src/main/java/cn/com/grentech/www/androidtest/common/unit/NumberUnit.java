package cn.com.grentech.www.androidtest.common.unit;

/**
 * Created by Administrator on 2017/4/5.
 */

public class NumberUnit {
    public static boolean isNumber(String s)
    {
        byte[] result=s.getBytes();
        for (byte line:result)
        {
            if(line>=48&&line<=57)
            {}
            else
            {
                return false;
            }
        }
        return true;
    }
}
