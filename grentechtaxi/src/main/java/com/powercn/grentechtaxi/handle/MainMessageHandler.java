package com.powercn.grentechtaxi.handle;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.common.http.HttpRequestParam;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.http.ResponeInfo;
import com.powercn.grentechtaxi.common.unit.DateUnit;
import com.powercn.grentechtaxi.common.unit.GsonUnit;
import com.powercn.grentechtaxi.entity.CallOrderStatusEnum;
import com.powercn.grentechtaxi.entity.LoginInfo;
import com.powercn.grentechtaxi.entity.OrderInfo;
import com.powercn.grentechtaxi.entity.ResponseDataListModel;
import com.powercn.grentechtaxi.entity.ResponseUerInfo;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.powercn.grentechtaxi.common.unit.GsonUnit.toObject;

/**
 * Created by Administrator on 2017/5/11.
 */

public class MainMessageHandler extends Handler {
    WeakReference<MainActivity> mActivity;

    public MainMessageHandler(MainActivity activity) {
        mActivity = new WeakReference(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            MainActivity activity = (MainActivity) this.mActivity.get();
            if (activity != null) {
                if (msg.obj != null && msg.obj instanceof ResponeInfo) {
                    ResponeInfo responeInfo = (ResponeInfo) msg.obj;
                    HttpRequestParam.ApiType apiType = responeInfo.getApiType();
                    Map map = null;
                    String info="";
                    Boolean success=false;
                    switch (apiType) {
                        case SendSmsCrc:
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                            Double d = (Double) map.get("code");
                            activity.loginView.getTv_login_crc().setText(String.valueOf(d.intValue()));
                            break;
                        case LoginBySmsCrc:
                            // {"success":true,"nickName":null,"headImage":null,"message":"登录成功","token":"3C5D207F7DF8C6261209A3BFF9CE3F2F"}
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                              success = (Boolean)map.get("success");
                            info=map.get("message").toString();

                            if (success==true) {
                                activity.showToast(info);
                                activity.loginView.getView().setVisibility(View.GONE);
                                LoginInfo loginInfo = new LoginInfo();
                                loginInfo.doLoginSuccess = true;
                                loginInfo.phone = activity.loginView.getTv_login_phone().getText().toString();
                                loginInfo.uuid = activity.deviceuuid;
                                LoginInfo.saveUserLoginInfo(activity, loginInfo);
                                activity.currentLoginSuccess = true;
                                HttpRequestTask.getUserInfo(loginInfo.phone);
                            } else {
                                activity.showToast(info);
                                activity.currentLoginSuccess = false;
                            }
                            break;
                        case LoginByUuid:
                            List<OrderInfo> list=new ArrayList<>();

                            for(int i=1;i<10;i++)
                            {
                               if(i==1)
                               {
                                   OrderInfo orderInfo1=new OrderInfo(i,new Date(),"办公室","家", CallOrderStatusEnum.RUNNING);
                                   list.add(orderInfo1);
                               }
                                else
                               {
                                   OrderInfo orderInfo1=new OrderInfo(i,new Date(),"办公室","家", CallOrderStatusEnum.FINISH);
                                   list.add(orderInfo1);
                               }
                            }
                            activity.tripOrderView.updateData(list);
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean)map.get("success");
                            info=map.get("message").toString();
                            if (success==true) {
                                activity.showToast(info);
                                activity.loginView.getView().setVisibility(View.GONE);
                                activity.currentLoginSuccess = true;

                                HttpRequestTask.getUserInfo(activity.loginInfo.phone);
                            } else {
                                activity.showToast(info);
                                activity.currentLoginSuccess = false;
                            }
                            break;

                        case GetAllOrderByMobile:
                            ResponseDataListModelext model = (ResponseDataListModelext) toObject(responeInfo.getJson(), ResponseDataListModelext.class);
                            activity.tripOrderView.updateData(model.getList());
                            break;
                        case GetUserInfo:
                            //{"success":true,"nickName":null,"headImage":null,"message":"取数据成功"}
                           activity.responseUerInfo=(ResponseUerInfo) GsonUnit.toObject(responeInfo.getJson(), ResponseUerInfo.class);
                            break;
                        case UpFile:
                            //{"success":true,"headImage":"c:\\1234VVTT.png","message":"上传成功"}
                            map = (Map) toObject(responeInfo.getJson(), Map.class);

                            success = (Boolean)map.get("success");
                            info=map.get("message").toString();
                            if(success==true)
                            {
                                activity.responseUerInfo.setHeadImage(map.get("headImage").toString());

                            }else
                            {
                                activity.upBitmap=null;

                            }
                            activity.showToast(info);
                            break;
                        case SaveUserInfo:
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean)map.get("success");
                            info=map.get("message").toString();
                            if(success==true)
                            {
                                if(activity.upBitmap!=null)
                                {
                                    activity.savefile(activity.upBitmap);
                                    activity.mainMapView.getIvHead().setImageBitmap(activity.upBitmap);
                                }
                            }
                            break;
                    }
                }
            }
            super.handleMessage(msg);
        } catch (Exception e) {
            System.out.println("handleMessage Error");
        }
    }
       @Data
    class  ResponseDataListModelext
    {
        private boolean success = true;
        private List<OrderInfo> list;
        private int count=0;
    }
}
