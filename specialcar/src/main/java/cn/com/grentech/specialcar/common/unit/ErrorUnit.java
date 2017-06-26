package cn.com.grentech.specialcar.common.unit;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Administrator on 2017/6/23.
 */

public class ErrorUnit {

    public static void println(String tag ,Exception exception)
    {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringUnit.println(tag,String.valueOf(stackTrace));
    }
}
