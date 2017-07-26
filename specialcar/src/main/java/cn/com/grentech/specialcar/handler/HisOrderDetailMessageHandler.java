package cn.com.grentech.specialcar.handler;

import android.os.Message;

import java.util.Map;

import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.activity.HisOrderDetailActivity;
import cn.com.grentech.specialcar.activity.OrderDetailActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestParam;
import cn.com.grentech.specialcar.common.http.ResponeInfo;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;

/**
 * Created by Administrator on 2017/6/30.
 */

public class HisOrderDetailMessageHandler extends AbstractHandler {
    public HisOrderDetailMessageHandler(AbstractBasicActivity activity) {
        super(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            HisOrderDetailActivity activity = (HisOrderDetailActivity) this.mActivity.get();
            if (activity != null) {
                if (msg.obj != null && msg.obj instanceof ResponeInfo) {
                    ResponeInfo responeInfo = (ResponeInfo) msg.obj;
                    HttpRequestParam.ApiType apiType = responeInfo.getApiType();
                    Map map = null;
                    String info = "";
                    Boolean success = false;
                    switch (apiType) {
                        case ReUPGps:
                            map = (Map) GsonUnit.toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            if (success) {
                                activity.showToast("补传成功");
                                StringUnit.println(tag,"补传成功");
                                activity.getBtReUp().setEnabled(false);
                            } else {
                                info = map.get("msg").toString();
                                activity.showToast(info);
                                activity.getBtReUp().setEnabled(true);
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
