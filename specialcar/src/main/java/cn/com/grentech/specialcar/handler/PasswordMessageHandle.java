package cn.com.grentech.specialcar.handler;

import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.Map;

import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.activity.DriverInfoActivity;
import cn.com.grentech.specialcar.activity.EditPasswordActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestParam;
import cn.com.grentech.specialcar.common.http.ResponeInfo;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.UserAllMap;

import static cn.com.grentech.specialcar.common.unit.GsonUnit.toObject;

/**
 * Created by Administrator on 2017/6/15.
 */

public class PasswordMessageHandle extends AbstractHandler {
    public PasswordMessageHandle(AbstractBasicActivity activity) {
        super(activity);
    }

    //    WeakReference<EditPasswordActivity> mActivity;
//    public PasswordMessageHandle(EditPasswordActivity activity) {
//        mActivity = new WeakReference(activity);
//    }
    @Override
    public void handleMessage(Message msg) {
        try {
            EditPasswordActivity activity = (EditPasswordActivity) this.mActivity.get();
            if (activity != null) {
                if (msg.obj != null && msg.obj instanceof ResponeInfo) {
                    ResponeInfo responeInfo = (ResponeInfo) msg.obj;
                    HttpRequestParam.ApiType apiType = responeInfo.getApiType();
                    Map map = null;
                    String info="";
                    Boolean success=false;
                    switch (apiType) {
                        case updateDriver:
                            map = (Map) GsonUnit.toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            if(success)
                            {
                                activity.showToast("修改成功");
                            }
                            else
                            {
                                info = map.get("msg").toString();
                                activity.showToast(info);
                            }
                            break;
                    }
                }
            }
            super.handleMessage(msg);
        } catch (Exception e) {
            ErrorUnit.println(tag,e);
            StringUnit.println(tag,"LoginhandleMessage Error");
        }
    }
}
