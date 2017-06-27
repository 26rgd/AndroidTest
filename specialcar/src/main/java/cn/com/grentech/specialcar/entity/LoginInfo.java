package cn.com.grentech.specialcar.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.Serializable;

import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/5/9.
 */

public class LoginInfo implements Serializable {
    private static String tag = LoginInfo.class.getName();
    private static final long serialVersionUID = -8616073288464289507L;
    public static LoginInfo loginInfo;
    public Boolean doLoginSuccess;//是否做过成功的登录动作,切换用户后为false;
    public String phone;
    public String uuid;
    public String password;
    public String bitmapPath = "";

    public static void saveUserLoginInfo(Context context, LoginInfo loginInfo) {

        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences("LoginInfo", MODE_PRIVATE).edit();
        editor.putBoolean("doLoginSuccess", loginInfo.doLoginSuccess);
        editor.putString("phone", loginInfo.phone);
        editor.putString("uuid", loginInfo.uuid);
        editor.putString("password", loginInfo.password);
        editor.putString("bitmapPath", loginInfo.bitmapPath);
        editor.commit();
    }


    public static LoginInfo readUserLoginInfo(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("LoginInfo", MODE_PRIVATE);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.doLoginSuccess = sharedPreferences.getBoolean("doLoginSuccess", false);
        loginInfo.phone = sharedPreferences.getString("phone", "");
        loginInfo.uuid = sharedPreferences.getString("uuid", "");
        loginInfo.password = sharedPreferences.getString("password", "");
        loginInfo.bitmapPath = sharedPreferences.getString("bitmapPath", "");
        return loginInfo;
    }


    public static String getUuid(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            String uuid = Build.SERIAL;
            return uuid;
        } else {
            String uuid = telephonyManager.getDeviceId();
            if (uuid == null || uuid.length() == 0)
                uuid = Build.SERIAL;
            return uuid;
        }
    }

    public static void saveProcessOrder(Context context, Order info) {
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences("ProcessOrder", MODE_PRIVATE).edit();
        editor.putString("orderdetail", GsonUnit.toJson(info));
        StringUnit.println(tag,  "saveProcessOrder|"+GsonUnit.toJson(info));
        editor.commit();
    }

    public static Order readProcessOrder(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("ProcessOrder", MODE_PRIVATE);
        String data = sharedPreferences.getString("orderdetail", "No Order");
        StringUnit.println(tag, "readProcessOrder|"+data);
        try {
            return (Order) GsonUnit.toObject(data, Order.class);
        } catch (Exception e) {
            return null;
        }
    }


    public static void saveLoadLine(Context context, LoadLine info) {
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(LoadLine.class.getSimpleName(), MODE_PRIVATE).edit();
        editor.putString(info.getOrderinfo().getId()+"", GsonUnit.toJson(info));
        editor.commit();
    }

    public static LoadLine readLoadLine(Context context, Order info) {

        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(LoadLine.class.getSimpleName(), MODE_PRIVATE);
        String data = sharedPreferences.getString(info.getId()+"", "No LoadLine");
        try {
            StringUnit.println(tag, data);
            return (LoadLine) GsonUnit.toObject(data, LoadLine.class);
        } catch (Exception e) {
            return null;
        }
    }
}
