package cn.com.grentech.specialcar.common.unit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;

import static cn.com.grentech.specialcar.common.unit.CoordinateSystem.pi;

/**
 * Created by Administrator on 2017/7/3.
 */

public class AlarmUnit {
    private static String tag = AlarmUnit.class.getName();
    private static AlarmManager alarm = null;
    private static PendingIntent pi = null;
    private static int delayTime = 21 * 60 * 1000;

    public static void startAlarm(Context context, Class<?> cls) {
        try {
            Intent intent = new Intent(context, cls);
            pi = PendingIntent.getActivity(context, 1, intent, 0);
            int type = AlarmManager.ELAPSED_REALTIME_WAKEUP;
            if (alarm == null)
                alarm = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
            if (alarm != null) {
                alarm.cancel(pi);
                // 闹钟在系统睡眠状态下会唤醒系统并执行提示功能
                StringUnit.println(tag, "唤醒系统alarm");
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    alarm.setWindow(type, SystemClock.elapsedRealtime() + delayTime, 0, pi);
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarm.setExactAndAllowWhileIdle(type, SystemClock.elapsedRealtime() + delayTime, pi);
                } else
                    alarm.set(type, SystemClock.elapsedRealtime() + delayTime, pi);
            }
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        }
    }

    public static void startAlarm(Context context, Class<?> cls,int delay) {
        try {
            Intent intent = new Intent(context, cls);
            pi = PendingIntent.getActivity(context, 1, intent, 0);
            int type = AlarmManager.ELAPSED_REALTIME_WAKEUP;
            if (alarm == null)
                alarm = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
            if (alarm != null) {
                alarm.cancel(pi);
                // 闹钟在系统睡眠状态下会唤醒系统并执行提示功能
                StringUnit.println(tag, "唤醒系统alarm");
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    alarm.setWindow(type, SystemClock.elapsedRealtime() + delay, 0, pi);
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarm.setExactAndAllowWhileIdle(type, SystemClock.elapsedRealtime() + delay, pi);
                } else
                    alarm.set(type, SystemClock.elapsedRealtime() + delay, pi);
            }
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        }
    }

    public static void cancelAlarm() {
        StringUnit.println(tag, "cancelAlarm()" + alarm);
        if (alarm != null) {
            try {
                StringUnit.println(tag, "清除alarm");
                alarm.cancel(pi);
                alarm = null;
            } catch (Exception e) {
                ErrorUnit.println(tag, e);
            }
        }
    }


}
