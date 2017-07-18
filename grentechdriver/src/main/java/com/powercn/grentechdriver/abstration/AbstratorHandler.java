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

    private Map<String, WeakReference<Object>> map;

    protected AbstratorHandler() {
        map = new HashMap<>();
    }



    public void addObejct(String key, Object object) {
        WeakReference<Object> mObject = new WeakReference(object);
        map.put(key, mObject);
    }

    public Object getByKey(String key)
    {
        return map.get(key);
    }


}
