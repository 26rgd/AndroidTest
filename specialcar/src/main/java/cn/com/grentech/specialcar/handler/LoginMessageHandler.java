package cn.com.grentech.specialcar.handler;

import android.content.Intent;
import android.os.Message;


import java.util.Map;

import cn.com.grentech.specialcar.activity.MainActivity;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.activity.LoginActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestParam;
import cn.com.grentech.specialcar.common.http.ResponeInfo;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.service.ServiceAddr;

import static cn.com.grentech.specialcar.common.unit.GsonUnit.toObject;

import static cn.com.grentech.specialcar.entity.LoginInfo.loginInfo;


/**
 * Created by Administrator on 2017/6/1.
 */

public class LoginMessageHandler extends AbstractHandler {
    public LoginMessageHandler(AbstractBasicActivity activity) {
        super(activity);
    }

//    WeakReference<LoginActivity> mActivity;
//
//    public LoginMessageHandler(LoginActivity activity) {
//        mActivity = new WeakReference(activity);
//    }

    @Override
    public void handleMessage(Message msg) {
        try {
            LoginActivity activity = (LoginActivity) this.mActivity.get();
            if (activity != null) {
                if (msg.obj != null && msg.obj instanceof ResponeInfo) {
                    ResponeInfo responeInfo = (ResponeInfo) msg.obj;
                    HttpRequestParam.ApiType apiType = responeInfo.getApiType();
                    Map map = null;
                    String info = "";
                    Boolean success = false;
                    switch (apiType) {
                        case SendSmsCrc:

                            break;
                        case Login:
                            map = (Map) GsonUnit.toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            if (success != null && success) {
                                LoginInfo.loginInfo = new LoginInfo();
                                loginInfo.phone = activity.getTvUserName().getText().toString().trim();
                                loginInfo.uuid = LoginInfo.getUuid(activity);
                                loginInfo.password=activity.getTvPassword().getText().toString().trim();
                                loginInfo.doLoginSuccess = true;
                               // activity.startService(ServiceAddr.class);
                                LoginInfo.saveUserLoginInfo(activity, loginInfo);
                                activity.jumpFinish(MainActivity.class);
                            } else {
                                info = map.get("msg").toString();
                                activity.showToast(info);
                            }
                            break;
                    }
                }
            }
            super.handleMessage(msg);
        } catch (Exception e) {
            StringUnit.println(tag, "LoginhandleMessage Error");
        }
    }



}
