package com.powercn.grentechdriver.abstration;

import android.app.Service;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.powercn.grentechdriver.common.unit.ErrorUnit;


/**
 * Created by Administrator on 2017/6/16.
 */

public abstract class AbstractService extends Service {
    protected String tag=this.getClass().getName();
    public void showToast(String msg) {
        try {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ErrorUnit.println(tag,e);

        }
    }
    public void showToastLength(String msg) {
        try {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            ErrorUnit.println(tag,e);

        }
    }
    public abstract AbstratorHandler getAbstratorHandler();
    public void sendHandleMessage(String key, String content, Object object) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(key, content);
            Message msg = new Message();
            msg.what = 0;
            msg.setData(bundle);
            msg.obj = object;
            Handler handler = getAbstratorHandler();
            handler.sendMessage(msg);
        } catch (Exception e) {
            ErrorUnit.println(tag,e);

        }
    }
}
