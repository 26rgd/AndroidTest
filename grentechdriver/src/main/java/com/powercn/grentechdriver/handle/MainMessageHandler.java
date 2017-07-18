package com.powercn.grentechdriver.handle;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.powercn.grentechdriver.activity.MainActivity;
import com.powercn.grentechdriver.activity.LoginActivity;
import com.powercn.grentechdriver.common.http.HttpRequestParam;
import com.powercn.grentechdriver.common.http.HttpRequestTask;
import com.powercn.grentechdriver.common.http.ResponeInfo;
import com.powercn.grentechdriver.common.unit.ErrorUnit;
import com.powercn.grentechdriver.common.unit.GsonUnit;
import com.powercn.grentechdriver.common.unit.StringUnit;
import com.powercn.grentechdriver.entity.LoginInfo;
import com.powercn.grentechdriver.entity.OrderInfo;
import com.powercn.grentechdriver.entity.ResponseUerInfo;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import lombok.Data;

import static com.powercn.grentechdriver.common.unit.GsonUnit.toObject;

/**
 * Created by Administrator on 2017/5/11.
 */

public class MainMessageHandler extends Handler {
    private String tag=this.getClass().getName();
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
                    String filename="";
                    switch (apiType) {
                        case LoginByUuid:
                            map = (Map) toObject(responeInfo.getJson(), Map.class);
                            success = (Boolean) map.get("success");
                            info = map.get("message").toString();
                            if (success == true) {
                                activity.showToast(info);
                                // activity.loginView.getView().setVisibility(View.GONE);
                                LoginInfo.currentLoginSuccess = true;
                                HttpRequestTask.getUserInfo(activity.loginInfo.phone);
                                String filepath=map.get("headImage").toString();

                                filename= getFileName(filepath);
                                if (!activity.loginInfo.bitmapPath.equals(filename))
                                {
                                    HttpRequestTask.loadFile(filepath);
                                }
                            } else {
                                activity.showToast(info);
                               // activity.jumpForResult(LoginActivity.class, MainActivity.otherCode);
                                LoginInfo.currentLoginSuccess = false;
                            }
                            break;

                        case GetAllOrderByMobile:
                            ResponseDataListModelext model = (ResponseDataListModelext) toObject(responeInfo.getJson(), ResponseDataListModelext.class);

                            break;
                        case GetUserInfo:
                            //{"success":true,"nickName":null,"headImage":null,"message":"取数据成功"}
                            activity.responseUerInfo = (ResponseUerInfo) GsonUnit.toObject(responeInfo.getJson(), ResponseUerInfo.class);
                            filename= getFileName(activity.responseUerInfo.getHeadImage());
                           if (!activity.loginInfo.bitmapPath.equals(filename))
                           {
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
                                 filename= getFileName(map.get("headImage").toString());
                                activity.savefile(activity.upBitmap,filename);
                                activity.loginInfo.bitmapPath=filename;
                                LoginInfo.saveUserLoginInfo(activity,activity.loginInfo);
                                activity.userInfoView.getUserAdpter().getHead().setImageBitmap(activity.upBitmap);

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
                                Bitmap bitmap=(Bitmap)responeInfo.getObject();

                                filename= getFileName(responeInfo.getUrl());
                                activity.savefile(bitmap,filename);
                                activity.loginInfo.bitmapPath=filename;
                                LoginInfo.saveUserLoginInfo(activity,activity.loginInfo);
                            }catch (Exception e)
                        {

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

    @Data
    class ResponseDataListModelext {
        private boolean success = true;
        private List<OrderInfo> list;
        private int count = 0;
    }

    private String getFileName(String path)
    {
        String filename="";
       if (!StringUnit.isEmpty(path))
       {
           int i=path.lastIndexOf("/")+1;
           filename=path.substring(i);
       }
        return filename;
    }
}
