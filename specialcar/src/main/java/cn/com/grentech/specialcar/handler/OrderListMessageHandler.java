package cn.com.grentech.specialcar.handler;

import android.os.Message;

import java.util.Map;

import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.activity.OrderListActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestParam;
import cn.com.grentech.specialcar.common.http.ResponeInfo;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.OrderListMap;

import static cn.com.grentech.specialcar.common.unit.GsonUnit.toObject;

/**
 * Created by Administrator on 2017/6/15.
 */

public class OrderListMessageHandler extends AbstractHandler {
    public OrderListMessageHandler(AbstractBasicActivity activity) {
        super(activity);
    }

    //    WeakReference<OrderListActivity> mActivity;
//    public OrderListMessageHandler(OrderListActivity activity) {
//        mActivity = new WeakReference(activity);
//    }
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
                        case GetOrderList:
                            OrderListMap object = (OrderListMap) toObject(responeInfo.getJson(), OrderListMap.class);
                            activity.getOrderListAdapter().update(object.getRoot());
                            StringUnit.println(tag,object.toString());
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
