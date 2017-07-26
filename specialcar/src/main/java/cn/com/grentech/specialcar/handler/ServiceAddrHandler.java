package cn.com.grentech.specialcar.handler;

import android.os.Message;

import java.util.Map;

import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.activity.EditPasswordActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestParam;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.http.ResponeInfo;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.LoginInfo;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ServiceAddrHandler extends AbstractHandler {
    public ServiceAddrHandler(AbstractBasicActivity activity) {
        super(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        try {


            if (msg.obj != null && msg.obj instanceof ResponeInfo) {
                ResponeInfo responeInfo = (ResponeInfo) msg.obj;
                HttpRequestParam.ApiType apiType = responeInfo.getApiType();
                Map map = null;
                String info = "";
                Boolean success = false;
                switch (apiType) {
                    case GetAddr:
                    map = (Map) GsonUnit.toObject(responeInfo.getJson(), Map.class);
                    map= (Map) map.get("result");
                    String add=map.get("formatted_address").toString()+map.get("sematic_description").toString();
                        add=add.replace("广东省","");
                    HttpRequestTask.upDriverLocation(null, LoginInfo.loginInfo.phone,add);
                    break;
            }
            }

            super.handleMessage(msg);
        } catch (Exception e) {
            ErrorUnit.println(tag,e);
            StringUnit.println(tag, "ServiceAddrHandler Error");
        }
    }
}
