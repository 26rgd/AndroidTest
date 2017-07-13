package com.powercn.grentechdriver.error;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;
import android.os.Process;

import com.powercn.grentechdriver.common.unit.StringUnit;


/**
 * Created by Administrator on 2017/6/23.
 */

public class UnError implements Thread.UncaughtExceptionHandler {
    private String tag=this.getClass().getName();
    private Context context;


    public UnError(Context context) {
        this.context=context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringUnit.println(tag,String.valueOf(stackTrace));


        Process.killProcess(Process.myPid());
        System.exit(10);
    }
}
