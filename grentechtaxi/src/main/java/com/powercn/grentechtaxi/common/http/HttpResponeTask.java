package com.powercn.grentechtaxi.common.http;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.activity.LoginActivity;
import com.powercn.grentechtaxi.activity.OrderListActivity;
import com.powercn.grentechtaxi.common.unit.StringUnit;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HttpResponeTask {
    private static String tag=HttpResponeTask.class.getName();

    public static ResponeInfo onPostHttp(ResponeInfo responeInfo) {

        StringUnit.println(tag,responeInfo.getJson());
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
            case GetAllOrderByMobile:
                OrderListActivity.sendHandleMessage("data",responeInfo.getJson(),responeInfo);
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
