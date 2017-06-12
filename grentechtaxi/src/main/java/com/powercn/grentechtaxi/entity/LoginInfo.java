package com.powercn.grentechtaxi.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/5/9.
 */

public class LoginInfo implements Serializable {
    private static final long serialVersionUID = -8616073288464289507L;
    public static boolean currentLoginSuccess = false;
    public Boolean doLoginSuccess;//是否做过成功的登录动作,切换用户后为false;
    public String phone;
    public String uuid;
    public String bitmapPath="";

    public static void saveUserLoginInfo(Context context,LoginInfo loginInfo) {

        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences("LoginInfo", MODE_PRIVATE).edit();
        editor.putBoolean("doLoginSuccess", loginInfo.doLoginSuccess);
        editor.putString("phone", loginInfo.phone);
        editor.putString("uuid", loginInfo.uuid);
        editor.putString("bitmapPath", loginInfo.bitmapPath);
        editor.commit();
    }


    public static LoginInfo readUserLoginInfo(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("LoginInfo", MODE_PRIVATE);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.doLoginSuccess=sharedPreferences.getBoolean("doLoginSuccess", false);
        loginInfo.phone=sharedPreferences.getString("phone", "");
        loginInfo.uuid=sharedPreferences.getString("uuid", "");
        loginInfo.bitmapPath=sharedPreferences.getString("bitmapPath", "");
        return loginInfo;
    }





    public static String  getUuid(Context context)
    {
        TelephonyManager telephonyManager=(TelephonyManager)context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager==null)
        {
            String uuid= Build.SERIAL;
            return uuid;
        }
            else
        {
            String uuid= telephonyManager.getDeviceId();
            if(uuid==null||uuid.length()==0)
                uuid= Build.SERIAL;
            return uuid;
        }

    }
}
