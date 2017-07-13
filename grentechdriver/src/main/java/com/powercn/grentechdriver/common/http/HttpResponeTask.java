package com.powercn.grentechdriver.common.http;


import android.content.Context;
import android.content.Intent;

import com.powercn.grentechdriver.SysApplication;
import com.powercn.grentechdriver.common.unit.ErrorUnit;
import com.powercn.grentechdriver.common.unit.StringUnit;


/**
 * Created by Administrator on 2017/5/10.
 */

public class HttpResponeTask {
    private static String tag = HttpResponeTask.class.getName();

    public static ResponeInfo onPostHttp(ResponeInfo responeInfo) {
        StringUnit.println(tag, responeInfo.getJson());
        if (   (responeInfo.getJson().contains("登录") && responeInfo.getJson().contains("false") ) || responeInfo.getJson().contains("sessionout") ) {
            try {
//                StringUnit.println(tag, "启动LoginService");
//                Context context = SysApplication.getInstance().getContext();
//                HttpUnit.sessionId = null;
//                Intent intent = new Intent(context, ServiceLogin.class);
//                context.startService(intent);
            } catch (Exception e) {
                ErrorUnit.println(tag, e);
            }
            return responeInfo;
        }
        try {
            if (responeInfo.abstractBasicActivity != null)
                responeInfo.abstractBasicActivity.sendHandleMessage("data", responeInfo.getJson(), responeInfo);

            if (responeInfo.abstractService != null)
                responeInfo.abstractService.sendHandleMessage("data", responeInfo.getJson(), responeInfo);
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        }

        return responeInfo;
    }


}
