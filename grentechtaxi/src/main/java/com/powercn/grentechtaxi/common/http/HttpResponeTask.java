package com.powercn.grentechtaxi.common.http;

import com.powercn.grentechtaxi.MainActivity;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HttpResponeTask {

    public static ResponeInfo onPostHttp(ResponeInfo responeInfo) {
        System.out.println("***************************************************************");
        System.out.println(responeInfo.getJson());
        if (responeInfo.getJson().contains("未登录")) {
            return responeInfo;
        }
        switch (responeInfo.getApiType()) {
            case SendSmsCrc:
                MainActivity.sendHandleMessage("data",responeInfo.getJson(),responeInfo);
            break;
            case LoginBySmsCrc:
                MainActivity.sendHandleMessage("data",responeInfo.getJson(),responeInfo);
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
