package cn.com.grentech.www.androidtest.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;

import cn.com.grentech.www.androidtest.MainActivity;
import cn.com.grentech.www.androidtest.R;
import cn.com.grentech.www.androidtest.common.unit.StringUnit;

/**
 * Created by Administrator on 2017/4/25.
 */

public class GrentechService extends Service {
    private IntentService intentService;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StringUnit.println("service|" + Process.myPid());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        showNotification(pendingIntent);

        return START_STICKY;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("deprecation")
    public void showNotification(PendingIntent pendingIntent) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            notification = new Notification.Builder(this).setContentTitle("广东电信").setContentText("正在查看").setSmallIcon(R.drawable.search_icon).setContentIntent(pendingIntent).build();
        } else {
            long when = System.currentTimeMillis();
            notification = new Notification(R.drawable.search_icon, "广东电信", when + 1000);
        }
        startForeground(Process.myPid(), notification);
    }
}
