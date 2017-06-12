package com.powercn.grentechtaxi.service;

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

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.common.websocket.WebSocketTask;

import org.java_websocket.drafts.Draft_17;

import java.net.URI;

/**
 * Created by Administrator on 2017/6/7.
 */

public class WebSocketService extends Service {
    private String tag=this.getClass().getName();
    private IntentService intentService;
    private WebSocketTask c = null;
    private Boolean connection=false;
    private int count=0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StringUnit.println(tag,"service|" + Process.myPid());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        if(connection==false)
        {
            onOpen();
        }
        if(count==0)
        showNotification(pendingIntent);
        count=count+1;
        return START_STICKY;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("deprecation")
    public void showNotification(PendingIntent pendingIntent) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD&&Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(this).setContentTitle(getResources().getString(R.string.app_name)).setContentText("正在查看").setSmallIcon(R.drawable.search_icon).setContentIntent(pendingIntent).setAutoCancel(true).getNotification();
        }
       else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(this).setContentTitle(getResources().getString(R.string.app_name)).setContentText("正在查看").setSmallIcon(R.drawable.search_icon).setContentIntent(pendingIntent).setAutoCancel(true).build();
        }
        else {
            long when = System.currentTimeMillis();
            notification = new Notification(R.drawable.search_icon, getResources().getString(R.string.app_name), when + 1000);
        }
        notification.flags =Notification.FLAG_AUTO_CANCEL;


        startForeground(Process.myPid(), notification);
    }
    private void onOpen() {
        try {
            // WebSocketTask c = new WebSocketTask( new URI( "ws://192.168.5.42:8080/grentechTaxiWx/webSocketServer" ), new Draft_17() );
            c = new WebSocketTask(new URI(HttpRequestTask.webSocketurl), new Draft_17());
            c.connectBlocking();
            connection=true;
            sendWebSocket("");
        } catch (Exception e) {
            connection=false;
            e.printStackTrace();
        }
    }

    private void sendWebSocket(String msg) {
        try {
          //  c.send(MainActivity.phone);
          // StringUnit.println("注册手机号: "+MainActivity.phone);

        } catch (Exception e) {
            e.printStackTrace();
            connection=false;
        }
    }

    @Override
    public void onDestroy() {
        StringUnit.println(tag,"Servie Stop *******************");
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
