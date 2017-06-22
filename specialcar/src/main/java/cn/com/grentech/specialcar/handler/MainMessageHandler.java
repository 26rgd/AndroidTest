package cn.com.grentech.specialcar.handler;

import android.os.Message;


import java.util.Map;

import cn.com.grentech.specialcar.activity.MainActivity;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.common.http.HttpRequestParam;
import cn.com.grentech.specialcar.common.http.ResponeInfo;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.OrderListMap;

import static cn.com.grentech.specialcar.common.unit.GsonUnit.toObject;


/**
 * Created by Administrator on 2017/5/11.
 */

public class MainMessageHandler extends AbstractHandler {
    public MainMessageHandler(AbstractBasicActivity activity) {
        super(activity);
    }

//    WeakReference<MainActivity> mActivity;
//
//    public MainMessageHandler(MainActivity activity) {
//        mActivity = new WeakReference(activity);
//    }

    @Override
    public void handleMessage(Message msg) {
        try {
            MainActivity activity = (MainActivity) this.mActivity.get();
            if (activity != null) {
                if (msg.obj != null && msg.obj instanceof ResponeInfo) {
                    ResponeInfo responeInfo = (ResponeInfo) msg.obj;
                    HttpRequestParam.ApiType apiType = responeInfo.getApiType();
                    Map map = null;
                    String info = "";
                    Boolean success = false;
                    String filename = "";
                    switch (apiType) {

                        case GetNotFinsh:
                            OrderListMap object = (OrderListMap) toObject(responeInfo.getJson(), OrderListMap.class);
                            activity.getNotFinshAdapter().update(object.getRoot());
                            StringUnit.println(tag,object.toString());
                            activity.getRefresh().setRefreshing(false);
                            break;
                        case UpFile:
                            ;
                            break;
//                        case SaveUserInfo:
//                            map = (Map) toObject(responeInfo.getJson(), Map.class);
//                            success = (Boolean) map.get("success");
//                            info = map.get("message").toString();
//                            activity.showToast(info);
//                            break;

                        case LoadFile:

                            break;

                    }
                } else {

                }
            }
            super.handleMessage(msg);
        } catch (Exception e) {
            StringUnit.println(tag, "MainhandleMessage Error");
        }
    }





    private String getFileName(String path) {
        String filename = "";
        if (!StringUnit.isEmpty(path)) {
            int i = path.lastIndexOf("/") + 1;
            filename = path.substring(i);
        }
        return filename;
    }


}
