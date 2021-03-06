package com.powercn.grentechdriver.common.http;

import android.os.AsyncTask;

import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.common.unit.GsonUnit;
import com.powercn.grentechdriver.common.unit.StringUnit;
import com.powercn.grentechdriver.entity.CallOrder;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.powercn.grentechdriver.common.http.HttpRequestParam.ApiType.evaluate;

/**
 * Created by Administrator on 2017/3/17.
 */

public class HttpRequestTask {
    //http://192.168.13.52/taxi/app/gis/getGatheringInfo
   //public static String url = "http://192.168.5.54:8080/taxi/api/";
    public static String url = "http://192.168.13.99:8080/taxi/api/";
    //public static String url = "http://192.168.13.41:8080/taxi/app/";
    public final static Executor executor = Executors.newFixedThreadPool(3);


    public static void getSmsCrc(AbstractBasicActivity abstractBasicActivity,String phone) {
        //{"status":"200","token":6AFB3E6A177D4BF145B8B355BAA4B82F"vcode":485721}
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("passenger/getCode", HttpRequestParam.ApiType.SendSmsCrc);
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void loginByPassword(AbstractBasicActivity abstractBasicActivity,String username, String password) {
        //{"status":"200","token":17285A189F2E6958BA25FBB8D991671C"info":"用户已经注册登录成功"}
        HttpRequestParam httpRequestParam = bulidHttpRequestParamApp("login?", HttpRequestParam.ApiType.LoginBySmsCrc);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("username", username);
        password="21232f297a57a5a743894a0e4a801fc3";
        httpRequestParam.paramMap.put("password", password);
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void loginBySms(AbstractBasicActivity abstractBasicActivity,String phone, String smsCrc, String uuid) {
        //{"status":"200","token":17285A189F2E6958BA25FBB8D991671C"info":"用户已经注册登录成功"}
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("passenger/codeLogin", HttpRequestParam.ApiType.LoginBySmsCrc);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.paramMap.put("vcode", smsCrc);
        httpRequestParam.paramMap.put("uuid", uuid);
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void loginByUuid(AbstractBasicActivity abstractBasicActivity, String phone, String uuid) {
        //{"status":"200","token":6AFB3E6A177D4BF145B8B355BAA4B82F"vcode":485721}
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("passenger/uuidLogin", HttpRequestParam.ApiType.LoginByUuid);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.paramMap.put("uuid", uuid);
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void callOrder(CallOrder callOrder) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParamApp("callOrder/wxCallApi", HttpRequestParam.ApiType.BookOrder);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("order", GsonUnit.toJson(callOrder));
        bulidDefaultTask(httpRequestParam);
        //cancleOrder(callOrder.getPhone());
    }

    public static void cancelOrder(String phone) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParamApp("callOrder/wxCancelOrder", HttpRequestParam.ApiType.CancleOrder);
        httpRequestParam.paramMap.put("phone", phone);
        bulidDefaultTask(httpRequestParam);
    }


    public static void getAllOrderByMobile(String phone, int start, int limit) {
//   http://localhost:8080/taxi/app/callOrder/getAllOrderByMobile?phone=%2215811810630%22&start=1&limit=2

        HttpRequestParam httpRequestParam = bulidHttpRequestParamApp("callOrder/getAllOrderByMobile", HttpRequestParam.ApiType.GetAllOrderByMobile);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.paramMap.put("start", String.valueOf(start));
        httpRequestParam.paramMap.put("limit", String.valueOf(limit));
        bulidDefaultTask(httpRequestParam);
    }

    public static void getUserInfo(String phone) {

        HttpRequestParam httpRequestParam = bulidHttpRequestParam("passenger/getPassenger", HttpRequestParam.ApiType.GetUserInfo);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("phone", phone);
        bulidDefaultTask(httpRequestParam);
    }

    public static void saveUserInfo(String passengerInfo) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("passenger/savePassenger", HttpRequestParam.ApiType.SaveUserInfo);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("passengerInfo", passengerInfo);
        bulidDefaultTask(httpRequestParam);
    }

    public static void evaluate(int orderId, int star,String conten) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("passenger/evaluate", evaluate);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("orderId", String.valueOf(orderId));
        httpRequestParam.paramMap.put("evaluateAnswer", String.valueOf(star));
        httpRequestParam.paramMap.put("conten", conten);
        bulidDefaultTask(httpRequestParam);
    }

    public static void headUplod(String phone ,String filepath) {
        //{"status":"200","token":17285A189F2E6958BA25FBB8D991671C"info":"用户已经注册登录成功"}
        // if(phone.length()>0)return;
        // HttpRequestParam httpRequestParam = bulidHttpRequestParam("driver/uplod", HttpRequestParam.ApiType.UpFile);
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("passenger/headUpload", HttpRequestParam.ApiType.UpFile);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostFile;
        httpRequestParam.paramMap.put("filepath", filepath);
        httpRequestParam.paramMap.put("filekey", "file");
        httpRequestParam.paramMap.put("phone", phone);
        bulidDefaultTask(httpRequestParam);
    }

    public static void loadFile(String filepath) {
        //{"status":"200","token":17285A189F2E6958BA25FBB8D991671C"info":"用户已经注册登录成功"}
        // if(phone.length()>0)return;
        // HttpRequestParam httpRequestParam = bulidHttpRequestParam("driver/uplod", HttpRequestParam.ApiType.UpFile);
        HttpRequestParam httpRequestParam = bulidHttpRequestParamMedia(filepath, HttpRequestParam.ApiType.LoadFile);
        httpRequestParam.requestType = HttpRequestParam.RequestType.LoadFile;
        bulidDefaultTask(httpRequestParam);
    }
    //*************************************************************************************************************************
    private static HttpRequestParam bulidHttpRequestParam(String path, HttpRequestParam.ApiType apiType) {
        HttpRequestParam httpRequestParam = new HttpRequestParam();
        httpRequestParam.url = url + path;
        httpRequestParam.apiType = apiType;
        return httpRequestParam;
    }
    private static HttpRequestParam bulidHttpRequestParamApp(String path, HttpRequestParam.ApiType apiType) {
        HttpRequestParam httpRequestParam = new HttpRequestParam();
        httpRequestParam.url = url.replace("api","api") + path;
        httpRequestParam.apiType = apiType;
        return httpRequestParam;
    }
    private static HttpRequestParam bulidHttpRequestParamMedia(String path, HttpRequestParam.ApiType apiType) {
        HttpRequestParam httpRequestParam = new HttpRequestParam();
        httpRequestParam.url =   path;
        httpRequestParam.apiType = apiType;
        return httpRequestParam;
    }
    private static void bulidDefaultTask(HttpRequestParam requestConfig) {
        MainBackGroundTask mAuthTask = new MainBackGroundTask();
        mAuthTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestConfig);
    }

    private static void bulidExecutorTask(HttpRequestParam requestConfig) {
        MainBackGroundTask mAuthTask = new MainBackGroundTask();
        mAuthTask.executeOnExecutor(executor, requestConfig);
    }

    public static ResponeInfo executeHttp(HttpRequestParam... params) {
        ResponeInfo responeInfo = null;
        HttpRequestParam param = params[0];
        HttpUnit httpUnit = new HttpUnit(param.url, null);
        httpUnit.clear();
        Map<String, String> map = param.paramMap;
        for (Map.Entry<String, String> ent : map.entrySet()) {
            httpUnit.addParams(ent.getKey(), ent.getValue());
        }
        if (param.requestType == HttpRequestParam.RequestType.Post)
            responeInfo = httpUnit.executePost();
        else if(param.requestType == HttpRequestParam.RequestType.PostText)
            responeInfo = httpUnit.executePostText();
        else if(param.requestType == HttpRequestParam.RequestType.PostFile)
            responeInfo = httpUnit.executePostFile();
        else if(param.requestType == HttpRequestParam.RequestType.LoadFile)
            responeInfo = httpUnit.executeLoadFile();
        else responeInfo = httpUnit.executeGet();
        responeInfo.setApiType(param.apiType);
        responeInfo.abstractBasicActivity=param.abstractBasicActivity;
        return responeInfo;
    }


}
