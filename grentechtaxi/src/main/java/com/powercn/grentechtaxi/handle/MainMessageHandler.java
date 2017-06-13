package com.powercn.grentechtaxi.handle;

import android.graphics.Bitmap;
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
import com.powercn.grentechtaxi.activity.LoginActivity;
import com.powercn.grentechtaxi.common.http.HttpRequestParam;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.http.ResponeInfo;
import com.powercn.grentechtaxi.common.unit.DateUnit;
import com.powercn.grentechtaxi.common.unit.GsonUnit;
import com.powercn.grentechtaxi.common.unit.MapUnit;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.common.websocket.WebSocketTask;
import com.powercn.grentechtaxi.common.websocket.WebSocketThread;
import com.powercn.grentechtaxi.dialog.RequestProgressDialog;
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

import static android.R.id.list;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.powercn.grentechtaxi.common.unit.GsonUnit.toObject;
import static com.powercn.grentechtaxi.entity.CallOrderStatusEnum.NOTAXI;
import static com.powercn.grentechtaxi.entity.CallOrderStatusEnum.RESVER;

/**
 * Created by Administrator on 2017/5/11.
 */

public class MainMessageHandler extends Handler {
    protected String tag = this.getClass().getName();
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
                    String info = "";
                    Boolean success = false;
                    String filename = "";
                    switch (apiType) {
                        case LoginByUuid:
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            info = map.get("message").toString();
                            if (success == true) {
                                activity.showToast(info);
                                LoginInfo.currentLoginSuccess = true;
                                WebSocketThread.getInstance().start(activity.loginInfo.phone);
                                HttpRequestTask.getUserInfo(activity.loginInfo.phone);
                                String filepath = MapUnit.toString(map, "headImage");
                                filename = getFileName(filepath);
                                if (!activity.loginInfo.bitmapPath.equals(filename)) {
                                    HttpRequestTask.loadFile(filepath);
                                }
                                activity.mainMapView.getIvHead().setImageBitmap(activity.readHeadImage());
                            } else {
                                activity.showToast(info);
                                activity.jumpForResult(LoginActivity.class, MainActivity.otherCode);
                                LoginInfo.currentLoginSuccess = false;
                            }
                            break;


                        case GetUserInfo:
                            WebSocketThread.getInstance().setOnReceiver(new WebSocketTask.onReceiver() {
                                @Override
                                public void onReceive(String msg) {
                                    OrderInfo orderInfo = (OrderInfo) GsonUnit.toObject(msg, OrderInfo.class);
                                    handleWebSocket(orderInfo);
                                    StringUnit.println(tag, orderInfo.toString());
                                }
                            });
                            //{"success":true,"nickName":null,"headImage":null,"message":"取数据成功"}
                            activity.responseUerInfo = (ResponseUerInfo) GsonUnit.toObject(responeInfo.getJson(), ResponseUerInfo.class);
                            filename = getFileName(activity.responseUerInfo.getHeadImage());
                            if (!activity.loginInfo.bitmapPath.equals(filename)) {
                                HttpRequestTask.loadFile(activity.responseUerInfo.getHeadImage());
                            }
                            break;
                        case UpFile:
                            //{"success":true,"headImage":"c:\\1234VVTT.png","message":"上传成功"}
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            info = map.get("message").toString();
                            if (success == true) {
                                activity.responseUerInfo.setHeadImage(map.get("headImage").toString());
                                filename = getFileName(map.get("headImage").toString());
                                activity.savefile(activity.upBitmap, filename);
                                activity.loginInfo.bitmapPath = filename;
                                LoginInfo.saveUserLoginInfo(activity, activity.loginInfo);
                                activity.userInfoView.getUserAdpter().getHead().setImageBitmap(activity.upBitmap);
                                activity.mainMapView.getIvHead().setImageBitmap(activity.upBitmap);
                            } else {
                                activity.upBitmap = null;
                            }
                            activity.showToast(info);
                            break;
                        case SaveUserInfo:
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            info = map.get("message").toString();
                            activity.showToast(info);
                            break;

                        case LoadFile:
                            try {
                                Bitmap bitmap = (Bitmap) responeInfo.getObject();
                                activity.mainMapView.getIvHead().setImageBitmap(bitmap);
                                filename = getFileName(responeInfo.getUrl());
                                activity.savefile(bitmap, filename);
                                activity.loginInfo.bitmapPath = filename;
                                LoginInfo.saveUserLoginInfo(activity, activity.loginInfo);
                            } catch (Exception e) {
                                activity.mainMapView.getIvHead().setImageBitmap(null);
                            }
                            break;
                        case BookOrder:
                            break;
                    }
                } else {
                    String key = msg.getData().getString("data");
                    if (key.equals(RequestProgressDialog.dialogAction)) {
                        activity.jumpMianMapView(activity.callActionView);
                    }
                }
            }
            super.handleMessage(msg);
        } catch (Exception e) {
            StringUnit.println(tag, "MainhandleMessage Error");
        }
    }


    private void handleWebSocket(OrderInfo orderInfo) {
        MainActivity activity = (MainActivity) this.mActivity.get();
        if(activity==null)return;
        if(activity.callActionView.getRequestProgressDialog()!=null)
        {
            try {
                activity.callActionView.getRequestProgressDialog().hide();
                activity.callActionView.setRequestProgressDialog(null);
            }catch (Exception e)
            {

            }
        }
        if (orderInfo != null) {
            activity.orderInfo=orderInfo;
            switch (orderInfo.getStatus()) {
               case RESVER:
                break;
                case NEW:
                break;
                case PENDING:
                break;
                case  BOOKED:
                break;
                case NOCHECK:
                break;
                case  NOTAXI:
                    activity.postMessage(MainActivity.PostType.CallAction,NOTAXI);
                break;
                case  FINISH:
                break;
                case  CANCEL_ADMIN:
                break;
                case  CANCEL_PASSENGER:
                break;
                case  CANCEL_DRIVER:
                break;
                case  ASSIGN_CAR:
                break;
                case  RUNNING:
                break;
            }
        }
    }

    @Data
    class ResponseDataListModelext {
        private boolean success = true;
        private List<OrderInfo> list;
        private int count = 0;
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
