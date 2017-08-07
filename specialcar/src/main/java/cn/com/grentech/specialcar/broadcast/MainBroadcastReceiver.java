package cn.com.grentech.specialcar.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import cn.com.grentech.specialcar.SysApplication;
import cn.com.grentech.specialcar.activity.LoginActivity;
import cn.com.grentech.specialcar.activity.OrderDetailActivity;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.service.ServiceGPS;

import static android.R.attr.tag;

/**
 * Created by Administrator on 2017/6/21.
 */

public class MainBroadcastReceiver extends BroadcastReceiver {
    protected String tag=this.getClass().getName();
    public static String action_Addr="action_Addr";
    public static String action_Addr_key="GpsInfo";
    public static String action_GPS="action_GPS";
    public static String action_GPS_key="distance";
    public static String action_GPS_orderId="orderId";
    public MainBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        StringUnit.println(tag,intent.getAction());
        if(intent.getAction().equals("android.intent.action.USER_PRESENT"))
        {
            StringUnit.println(tag,intent.getAction());
            StringUnit.println(tag,"锁屏后开启桌面");
            //startService(context, ServiceGPS.class);
        }
        if(intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_OPENED"))
        {
            //使用推送
            StringUnit.println(tag,"打开极光推送通知");
           if( SysApplication.getInstance().getList()==null||SysApplication.getInstance().getList().size()==0)
           {
               StringUnit.println(tag,"打开升级");
               startActivity(context, LoginActivity.class);
           }
        }

    }
    public void startService(Context context,Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startService(intent);

    }

    public void startActivity(Context context,Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);

    }

    public void showToast(Context context,String msg) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ErrorUnit.println(tag,e);
        }
    }
}
