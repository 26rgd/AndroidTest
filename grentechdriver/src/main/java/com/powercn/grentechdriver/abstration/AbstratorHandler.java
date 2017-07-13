package com.powercn.grentechdriver.abstration;

import android.os.Handler;
import android.os.Message;

import com.powercn.grentechdriver.common.http.HttpRequestParam;
import com.powercn.grentechdriver.common.http.ResponeInfo;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/11.
 */

public class AbstratorHandler extends Handler {

    protected String tag = this.getClass().getName();
    private static AbstratorHandler handler;
    private Map<String, WeakReference<Object>> map;

    private AbstratorHandler() {
        map = new HashMap<>();
    }

    public static AbstratorHandler getInstance() {
        if (handler == null)
            handler = new AbstratorHandler();
        return handler;
    }

    public void addObejct(String key, Object object) {
        WeakReference<Object> mObject = new WeakReference(object);
        map.put(key, mObject);
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.obj != null && msg.obj instanceof ResponeInfo)
        {
            ResponeInfo responeInfo = (ResponeInfo) msg.obj;
            HttpRequestParam.ApiType apiType = responeInfo.getApiType();
        }
    }
}
