package cn.com.grentech.specialcar.handler;

import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.Map;

import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.activity.DriverInfoActivity;
import cn.com.grentech.specialcar.activity.OrderDetailActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestParam;
import cn.com.grentech.specialcar.common.http.ResponeInfo;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.OrderStatus;
import cn.com.grentech.specialcar.entity.UserAllMap;
import cn.com.grentech.specialcar.service.ServiceGPS;

import static cn.com.grentech.specialcar.common.http.HttpRequestParam.ApiType.orderPause;
import static cn.com.grentech.specialcar.common.http.HttpRequestParam.ApiType.orderStart;
import static cn.com.grentech.specialcar.common.unit.GsonUnit.toObject;

/**
 * Created by Administrator on 2017/6/15.
 */

public class OrderDetailMessageHandle extends AbstractHandler {
    public OrderDetailMessageHandle(AbstractBasicActivity activity) {
        super(activity);
    }


    @Override
    public void handleMessage(Message msg) {
        try {
            OrderDetailActivity activity = (OrderDetailActivity) this.mActivity.get();
            if (activity != null) {
                if (msg.obj != null && msg.obj instanceof ResponeInfo) {
                    ResponeInfo responeInfo = (ResponeInfo) msg.obj;
                    HttpRequestParam.ApiType apiType = responeInfo.getApiType();
                    Map map = null;
                    String info = "";
                    Boolean success = false;
                    switch (apiType) {
                        case orderStart:
                            map = (Map) GsonUnit.toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            if (success) {
                                activity.doStartResult() ;
                                activity.showToast("执行成功");
                            } else {
                                info = map.get("msg").toString();
                                activity.showToast(info);
                            }
                            break;
                        case orderContinue:
                            map = (Map) GsonUnit.toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            if (success) {
                                activity.doContinueResult();
                                activity.showToast("执行成功");
                            } else {
                                info = map.get("msg").toString();
                                activity.showToast(info);
                            }
                            break;
                        case orderPause:
                            map = (Map) GsonUnit.toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            if (success) {
                                activity.doPauseResult();
                                activity.showToast("执行成功");
                            } else {
                                info = map.get("msg").toString();
                                activity.showToast(info);
                            }
                            break;
                        case orderFinish:
                            map = (Map) GsonUnit.toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            if (success) {
                                activity.doFinishResult();
                                activity.showToast("执行成功");

                            } else {
                                info = map.get("msg").toString();
                                activity.showToast(info);
                            }
                            break;

                        case ReUPGps:
                            map = (Map) GsonUnit.toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            if (success) {
                                activity.showToast("补传成功");
                                StringUnit.println(tag,"补传成功");

                            } else {
                                info = map.get("msg").toString();
                                activity.showToast(info);
                            }
                            break;
                    }
                }
            }
            super.handleMessage(msg);
        } catch (Exception e) {
            StringUnit.println(tag, e.toString());
        }
    }
}
