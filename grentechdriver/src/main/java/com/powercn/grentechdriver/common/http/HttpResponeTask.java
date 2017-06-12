package com.powercn.grentechdriver.common.http;

import com.powercn.grentechdriver.MainActivity;
import com.powercn.grentechdriver.activity.LoginActivity;
import com.powercn.grentechdriver.common.unit.StringUnit;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HttpResponeTask {

    public static ResponeInfo onPostHttp(ResponeInfo responeInfo) {
        StringUnit.println("***************************************************************");
        StringUnit.println(responeInfo.getJson());
        if (responeInfo.getJson().contains("未登录")) {
            return responeInfo;
        }
        switch (responeInfo.getApiType()) {
            case SendSmsCrc:
                LoginActivity.sendHandleMessage("data",responeInfo.getJson(),responeInfo);
            break;
            case LoginBySmsCrc:
                LoginActivity.sendHandleMessage("data",responeInfo.getJson(),responeInfo);
                break;
            case LoginByUuid:
                MainActivity.sendHandleMessage("data",responeInfo.getJson(),responeInfo);
                break;
            default:
                sendMessage(responeInfo);
        }
        return responeInfo;
    }

    private static  void sendMessage(ResponeInfo responeInfo)
    {
       try {
           MainActivity.sendHandleMessage("data",responeInfo.getJson(),responeInfo);
       }catch (Exception e)
       {
       }
    }
}
