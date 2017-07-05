package cn.com.grentech.specialcar.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.abstraction.AbstractService;
import cn.com.grentech.specialcar.activity.AlarmiInfoActivity;
import cn.com.grentech.specialcar.activity.LoginActivity;
import cn.com.grentech.specialcar.activity.MainActivity;
import cn.com.grentech.specialcar.activity.OrderDetailActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.http.HttpUnit;
import cn.com.grentech.specialcar.common.unit.AlarmUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.common.unit.WakeLockUnit;
import cn.com.grentech.specialcar.entity.LoginInfo;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ServiceMoitor extends AbstractService {

    private TimerTask monitorTask;
    private Timer timer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        StringUnit.println(tag,"serviceMonitor on Bind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        start(this);
    }

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();
    }

    @Override
    @SuppressWarnings("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {
        StringUnit.println(tag,"ServiceMoitor |" + Process.myPid());
//        if(StringUnit.isEmpty(HttpUnit.sessionId))
//        {
//            StringUnit.println(tag,"ServiceMoitor |" + HttpUnit.sessionId);
//            LoginInfo loginInfo=LoginInfo.readUserLoginInfo(getApplicationContext());
//            HttpRequestTask.loginByPassword(null,loginInfo.phone,loginInfo.password);
//        }
        Intent notificationIntent = new Intent(this, ServiceLogin.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        showNotification(pendingIntent);
        return super.onStartCommand(intent,START_STICKY,startId);
    }
    private void start(Context context) {
        if(monitorTask != null) {
            monitorTask.cancel();
        }
        timer = new Timer();
        monitorTask = new MonitorTask(context);
        timer.scheduleAtFixedRate(monitorTask, 0, 60000);
    }


    private void stop() {
        StringUnit.println(tag, "STOP SERVICEMONITOR method.........");
        monitorTask.cancel();
        timer.cancel();
    }
    class MonitorTask extends TimerTask {
        private Context context;
        private String packageName;

        public MonitorTask(Context context) {
            this.context = context;
            packageName = context.getPackageName();
        }

        @Override
        public void run() {
           // StringUnit.println(tag,"MonitorTask.......");
          //  WakeLockUnit.acquireWakeLock(ServiceMoitor.this.getApplicationContext());
            Intent intent1=new Intent(ServiceMoitor.this,ServiceGPS.class);
            startService(intent1);
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appInfos = am.getRunningAppProcesses();
            boolean  flag = false;
            for(ActivityManager.RunningAppProcessInfo appInfo : appInfos) {
                if(appInfo.processName.equals(packageName)) {
                    flag = true;
                    break;
                }
            }
             if(!flag)
            StringUnit.println(tag,"进后入台中.......");
        }
    }



    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("deprecation")
    public void showNotification(PendingIntent pendingIntent) {
        //if(1==1)return;
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD&&Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN) {
          //  notification = new Notification.Builder(this).setContentTitle(getResources().getString(R.string.app_name)).setContentText("运行中").setSmallIcon(R.drawable.grentech_logo).setContentIntent(pendingIntent).setAutoCancel(true).getNotification();

            notification = new Notification.Builder(this).setContentTitle(getResources().getString(R.string.app_name)).setSmallIcon(R.drawable.grentech_logo).setContentIntent(pendingIntent).setAutoCancel(true).getNotification();
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
           // notification = new Notification.Builder(this).setContentTitle(getResources().getString(R.string.app_name)).setContentText("运行中").setSmallIcon(R.drawable.grentech_logo).setContentIntent(pendingIntent).setAutoCancel(true).build();

            notification = new Notification.Builder(this).setContentTitle(getResources().getString(R.string.app_name)).setSmallIcon(R.drawable.grentech_logo).setContentIntent(pendingIntent).setAutoCancel(true).build();
        }
        else {
            long when = System.currentTimeMillis();
            notification = new Notification(R.drawable.grentech_logo, getResources().getString(R.string.app_name), when + 1000);
           // notification = new Notification(R.drawable.grentech_logo, null, when + 1000);
        }
        notification.flags =Notification.FLAG_AUTO_CANCEL;


        startForeground(Process.myPid(), notification);
       // startForeground(0, notification);
    }

    @Override
    public AbstractHandler getAbstratorHandler() {
        return null;
    }
}
