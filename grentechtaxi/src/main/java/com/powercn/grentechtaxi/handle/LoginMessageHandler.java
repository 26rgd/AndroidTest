package com.powercn.grentechtaxi.handle;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.activity.LoginActivity;
import com.powercn.grentechtaxi.common.http.HttpRequestParam;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.http.ResponeInfo;
import com.powercn.grentechtaxi.common.unit.GsonUnit;
import com.powercn.grentechtaxi.common.unit.MapUnit;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.common.websocket.WebSocketTask;
import com.powercn.grentechtaxi.common.websocket.WebSocketThread;
import com.powercn.grentechtaxi.entity.CallOrderStatusEnum;
import com.powercn.grentechtaxi.entity.LoginInfo;
import com.powercn.grentechtaxi.entity.OrderInfo;
import com.powercn.grentechtaxi.entity.ResponseUerInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.autonavi.ae.search.log.GLog.filename;
import static com.powercn.grentechtaxi.common.unit.GsonUnit.toObject;

/**
 * Created by Administrator on 2017/6/1.
 */

public class LoginMessageHandler extends Handler {
    protected String tag=this.getClass().getName();
    WeakReference<LoginActivity> mActivity;
    public LoginMessageHandler(LoginActivity activity) {
        mActivity = new WeakReference(activity);
    }
    @Override
    public void handleMessage(Message msg) {
        try {
            LoginActivity activity = (LoginActivity) this.mActivity.get();
            if (activity != null) {
                if (msg.obj != null && msg.obj instanceof ResponeInfo) {
                    ResponeInfo responeInfo = (ResponeInfo) msg.obj;
                    HttpRequestParam.ApiType apiType = responeInfo.getApiType();
                    Map map = null;
                    String info="";
                    Boolean success=false;
                    switch (apiType) {
                        case SendSmsCrc:
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                            Double d = (Double) map.get("code");
                            activity.getTv_login_crc().setText(String.valueOf(d.intValue()));
                            break;
                        case LoginBySmsCrc:
                            // {"success":true,"nickName":null,"headImage":null,"message":"登录成功","token":"3C5D207F7DF8C6261209A3BFF9CE3F2F"}
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean)map.get("success");
                            info=map.get("message").toString();
                            if (success==true) {
                                activity.showToast(info);
                                LoginInfo loginInfo = new LoginInfo();
                                loginInfo.doLoginSuccess = true;
                                loginInfo.phone = activity.getTv_login_phone().getText().toString();
                                loginInfo.uuid = activity.deviceuuid;
                                LoginInfo.saveUserLoginInfo(activity, loginInfo);
                                LoginInfo.currentLoginSuccess = true;
                                WebSocketThread.getInstance().start(loginInfo.phone);
                                String filepath= MapUnit.toString(map,"headImage");
                                filename= getFileName(filepath);
                                if (loginInfo.bitmapPath.equals(filename))
                                {
                                    HttpRequestTask.loadFile(filepath);
                                }
                                activity.finish();
                                HttpRequestTask.getUserInfo(loginInfo.phone);
                            } else {
                                activity.showToast(info);
                                LoginInfo.currentLoginSuccess = false;
                            }
                            break;
                    }
                }
            }
            super.handleMessage(msg);
        } catch (Exception e) {
            StringUnit.println(tag,"LoginhandleMessage Error");
        }
    }



    private String getFileName(String path)
    {
        String filename="";
        if (!StringUnit.isEmpty(path))
        {
            int i=path.lastIndexOf("/")+1;
            filename=path.substring(i);
        }
        return filename;
    }
}
