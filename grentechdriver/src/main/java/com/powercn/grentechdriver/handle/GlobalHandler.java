package com.powercn.grentechdriver.handle;

import android.os.Message;

import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.activity.LoginActivity;
import com.powercn.grentechdriver.activity.MainActivity;
import com.powercn.grentechdriver.common.http.HttpRequestParam;
import com.powercn.grentechdriver.common.http.HttpRequestTask;
import com.powercn.grentechdriver.common.http.ResponeInfo;
import com.powercn.grentechdriver.common.unit.ErrorUnit;
import com.powercn.grentechdriver.entity.LoginInfo;

import java.lang.ref.WeakReference;
import java.util.Map;

import static com.autonavi.ae.search.log.GLog.filename;
import static com.powercn.grentechdriver.common.unit.GsonUnit.toObject;
import static com.powercn.grentechdriver.common.unit.StringUnit.getFileName;

/**
 * Created by Administrator on 2017/7/13.
 */

public class GlobalHandler extends AbstratorHandler {

    private static AbstratorHandler handler;

    private GlobalHandler() {
        super();
    }

    public static AbstratorHandler getInstance() {
        if (handler == null)
            handler = new GlobalHandler();
        return handler;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.obj != null && msg.obj instanceof ResponeInfo) {
            ResponeInfo responeInfo = (ResponeInfo) msg.obj;
            AbstractBasicActivity ent = responeInfo.abstractBasicActivity;
            if (ent != null) {
                if (ent instanceof LoginActivity) {
                    handleMessageLogin(msg);
                } else if (ent instanceof MainActivity) {
                } else {
                }
            }
        }
    }

    public void handleMessageLogin(Message msg) {
        try {
            ResponeInfo responeInfo = (ResponeInfo) msg.obj;
            WeakReference<Object> wf=(WeakReference<Object>) this.getByKey(responeInfo.abstractBasicActivity.getClass().getName());
            LoginActivity activity = (LoginActivity) wf.get();
            if (activity != null) {
                if (msg.obj != null && msg.obj instanceof ResponeInfo) {

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

                                String filepath=map.get("headImage").toString();
                                filename= getFileName(filepath);
                                if (loginInfo.bitmapPath.equals(filename))
                                {
                                    HttpRequestTask.loadFile(filepath);
                                }

                                activity.jumpFinish(MainActivity.class);
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
            ErrorUnit.println(tag,e);
        }
    }
}
