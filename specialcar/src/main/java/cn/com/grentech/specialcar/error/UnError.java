package cn.com.grentech.specialcar.error;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.PrintWriter;
import java.io.StringWriter;
import android.os.Process;

import cn.com.grentech.specialcar.common.unit.AlarmUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.common.unit.WakeLockUnit;

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

//        Intent intent = new Intent(
//                "android.fbreader.action.CRASH",
//                new Uri.Builder().scheme(exception.getClass().getSimpleName()).build()
//        );
//        try {
//            myContext.startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            intent = new Intent(myContext, BugReportActivity.class);
//            intent.putExtra(BugReportActivity.STACKTRACE, stackTrace.toString());
//            myContext.startActivity(intent);
//        }

//        if (myContext instanceof Activity) {
//            ((Activity)myContext).finish();
//        }
        WakeLockUnit.releaseWakeLock();
        AlarmUnit.cancelAlarm();
        Process.killProcess(Process.myPid());
        System.exit(10);
    }
}
