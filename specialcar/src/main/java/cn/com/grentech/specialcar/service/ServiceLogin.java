package cn.com.grentech.specialcar.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;

import cn.com.grentech.specialcar.abstraction.AbstractService;
import cn.com.grentech.specialcar.activity.LoginActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.http.HttpUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.LoginInfo;

/**
 * Created by Administrator on 2017/6/26.
 */

public class ServiceLogin extends AbstractService {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    @SuppressWarnings("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {
        StringUnit.println(tag,"ServiceLogin |" + Process.myPid());
        if(StringUnit.isEmpty(HttpUnit.sessionId))
        {
            StringUnit.println(tag, "Service sessionid已经断开,自动登录一次");
            StringUnit.println(tag, HttpUnit.sessionId);
            LoginInfo loginInfo=LoginInfo.readUserLoginInfo(getApplicationContext());
            HttpRequestTask.loginByPassword(null,loginInfo.phone,loginInfo.password);
        }

        return super.onStartCommand(intent,START_STICKY,startId);
    }
}
