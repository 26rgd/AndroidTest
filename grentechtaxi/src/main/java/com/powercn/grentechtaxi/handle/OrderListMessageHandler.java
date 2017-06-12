package com.powercn.grentechtaxi.handle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.powercn.grentechtaxi.activity.LoginActivity;
import com.powercn.grentechtaxi.activity.OrderListActivity;
import com.powercn.grentechtaxi.common.http.HttpRequestParam;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.http.ResponeInfo;
import com.powercn.grentechtaxi.common.unit.MapUnit;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.common.websocket.WebSocketThread;
import com.powercn.grentechtaxi.entity.LoginInfo;

import java.lang.ref.WeakReference;
import java.util.Map;

import static com.autonavi.ae.search.log.GLog.filename;
import static com.powercn.grentechtaxi.MainActivity.mainMessageHandler;
import static com.powercn.grentechtaxi.common.unit.GsonUnit.toObject;

/**
 * Created by Administrator on 2017/6/9.
 */

public class OrderListMessageHandler  extends Handler {


    protected String tag=this.getClass().getName();
    WeakReference<OrderListActivity> mActivity;
    public OrderListMessageHandler(OrderListActivity activity) {
        mActivity = new WeakReference(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            OrderListActivity activity = (OrderListActivity) this.mActivity.get();
            if (activity != null) {
                if (msg.obj != null && msg.obj instanceof ResponeInfo) {
                    ResponeInfo responeInfo = (ResponeInfo) msg.obj;
                    HttpRequestParam.ApiType apiType = responeInfo.getApiType();
                    Map map = null;
                    String info="";
                    Boolean success=false;
                    switch (apiType) {
                        case GetAllOrderByMobile:
                            MainMessageHandler.ResponseDataListModelext model = (MainMessageHandler.ResponseDataListModelext) toObject(responeInfo.getJson(), MainMessageHandler.ResponseDataListModelext.class);
                            activity.updateData(model.getList());
                            break;
                        case LoginBySmsCrc:

                            break;
                    }
                }
            }
            super.handleMessage(msg);
        } catch (Exception e) {
            StringUnit.println(tag,"LoginhandleMessage Error");
        }
    }



}
