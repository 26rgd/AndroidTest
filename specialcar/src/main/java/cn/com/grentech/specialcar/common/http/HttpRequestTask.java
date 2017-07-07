package cn.com.grentech.specialcar.common.http;

import android.os.AsyncTask;




import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractService;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.entity.OrderStatus;

import static android.R.attr.id;
import static android.R.attr.path;
import static cn.com.grentech.specialcar.entity.OrderDetailInfo.flag;
import static cn.com.grentech.specialcar.entity.OrderDetailInfo.mileage;


/**
 * Created by Administrator on 2017/3/17.
 */

public class HttpRequestTask {
    private static String tag=HttpRequestTask.class.getName();
   // private static String ip="192.168.13.83";
 //  private static String ip="210.75.20.140";
  // private static String ip="120.77.243.254";
    private static String ip="taxi.powercn.com";
    public static String url = "http://"+ip+"/grentechZC/";
    public static String apkurl = "http://"+ip+"/apks/1.apk";
    public static String webSocketurl="ws://"+ip+"/taxi/api/webSocketServer";

    public final static Executor executor = Executors.newFixedThreadPool(3);



    public static void getSmsCrc(AbstractBasicActivity abstractBasicActivity,String phone) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("user/sendSmsVerify?", HttpRequestParam.ApiType.SendSmsCrc);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void loginByPassword(AbstractBasicActivity abstractBasicActivity,String phone, String password) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("user/login?", HttpRequestParam.ApiType.Login);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.paramMap.put("password", password);
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void logout(AbstractBasicActivity abstractBasicActivity) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("user/logout?", HttpRequestParam.ApiType.logout);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void resetPassword(AbstractBasicActivity abstractBasicActivity, String phone, String password, String smsVerify) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("user/resetPassword?", HttpRequestParam.ApiType.Login);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.paramMap.put("password", password);
        httpRequestParam.paramMap.put("smsVerify", smsVerify);
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void updateDriver(AbstractBasicActivity abstractBasicActivity, String phone, String name,
                                    String licenseNo, String address, String carNo, String carType,
                                    int siteCount) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("user/updateDriver?", HttpRequestParam.ApiType.updateDriver);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.paramMap.put("name", name);
        httpRequestParam.paramMap.put("licenseNo", licenseNo);

        httpRequestParam.paramMap.put("address", address);
        httpRequestParam.paramMap.put("carNo", carNo);
        httpRequestParam.paramMap.put("carType", carType);
        httpRequestParam.paramMap.put("siteCount", String.valueOf(siteCount));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void checkSession(AbstractBasicActivity abstractBasicActivity,String phone) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("user/checkSession?", HttpRequestParam.ApiType.checkSession);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void getNotFinsh(AbstractBasicActivity abstractBasicActivity,String driverPhone, int start, int limit) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("order/getByDriver?", HttpRequestParam.ApiType.GetNotFinsh);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("driverPhone", driverPhone);
        httpRequestParam.paramMap.put("start", String.valueOf(start));
        httpRequestParam.paramMap.put("limit", String.valueOf(limit));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void getOrderList(AbstractBasicActivity abstractBasicActivity,String driverPhone, int start, int limit) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("order/getHisByDriver?", HttpRequestParam.ApiType.GetOrderList);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("driverPhone", driverPhone);
        httpRequestParam.paramMap.put("start", String.valueOf(start));
        httpRequestParam.paramMap.put("limit", String.valueOf(limit));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void orderFinish(AbstractBasicActivity abstractBasicActivity,int id, double mileage, String bak, int flag) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("order/finish?", HttpRequestParam.ApiType.orderFinish);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("id", String.valueOf(id));
        httpRequestParam.paramMap.put("mileage", String.valueOf((int)mileage));
        httpRequestParam.paramMap.put("bak", bak);
        httpRequestParam.paramMap.put("flag", String.valueOf(flag));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void orderUpdateFlag(AbstractBasicActivity abstractBasicActivity,int id, int flag) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("order/updateFlag?", HttpRequestParam.ApiType.orderUpdateFlag);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("id", String.valueOf(id));
        httpRequestParam.paramMap.put("flag", String.valueOf(flag));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void orderStart(AbstractBasicActivity abstractBasicActivity,int id) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("order/updateFlag?", HttpRequestParam.ApiType.orderStart);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("id", String.valueOf(id));
        httpRequestParam.paramMap.put("flag", String.valueOf(OrderStatus.RunOrder.getValue()));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void orderPause(AbstractBasicActivity abstractBasicActivity,int id,double mileage) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("order/finish?", HttpRequestParam.ApiType.orderPause);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("id", String.valueOf(id));
        httpRequestParam.paramMap.put("mileage", String.valueOf((int)mileage));
        httpRequestParam.paramMap.put("bak", "p1");
        httpRequestParam.paramMap.put("flag", String.valueOf(OrderStatus.PauseOrder.getValue()));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void orderContinue(AbstractBasicActivity abstractBasicActivity,int id) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("order/updateFlag?", HttpRequestParam.ApiType.orderContinue);
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("id", String.valueOf(id));
        httpRequestParam.paramMap.put("flag", String.valueOf(OrderStatus.RunOrder.getValue()));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void upGps(AbstractBasicActivity abstractBasicActivity,String routes) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("route/uploadData?", HttpRequestParam.ApiType.UPGps);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("routes", (routes));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void reUpGps(AbstractBasicActivity abstractBasicActivity,String routes) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("route/uploadData?", HttpRequestParam.ApiType.ReUPGps);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("routes", (routes));
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void upDriverLocation(AbstractBasicActivity abstractBasicActivity,String phone,String location) {
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("user/updateLocation?", HttpRequestParam.ApiType.UpDrviceLocation);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("phone", phone);
        httpRequestParam.paramMap.put("location", location);
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void saveUserInfo(String passengerInfo) {
        StringUnit.println(tag,passengerInfo);
        HttpRequestParam httpRequestParam = bulidHttpRequestParam("passenger/savePassenger", HttpRequestParam.ApiType.LoadFile);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostText;
        httpRequestParam.paramMap.put("passengerInfo", passengerInfo);
        bulidDefaultTask(httpRequestParam);
    }



    public static void headUplod(String phone ,String filepath) {

        HttpRequestParam httpRequestParam = bulidHttpRequestParam("passenger/headUpload", HttpRequestParam.ApiType.UpFile);
        httpRequestParam.requestType = HttpRequestParam.RequestType.PostFile;
        httpRequestParam.paramMap.put("filepath", filepath);
        httpRequestParam.paramMap.put("filekey", "file");
        httpRequestParam.paramMap.put("phone", phone);
        bulidDefaultTask(httpRequestParam);
    }

    public static void loadFile(String filepath) {
        if(StringUnit.isEmpty(filepath))return;
        HttpRequestParam httpRequestParam = bulidHttpRequestParamMedia(filepath, HttpRequestParam.ApiType.LoadFile);
        httpRequestParam.requestType = HttpRequestParam.RequestType.LoadFile;
        bulidDefaultTask(httpRequestParam);
    }

//    http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=39.983424,116.322987&output=json&pois=1&ak=您的ak

    public static void getAddr(AbstractBasicActivity abstractBasicActivity, double lat, double lng) {
        //HttpRequestParam httpRequestParam = bulidHttpRequestParam("route/uploadData?", HttpRequestParam.ApiType.ReUPGps);
        HttpRequestParam httpRequestParam = new HttpRequestParam();
        httpRequestParam.url = "http://api.map.baidu.com/geocoder/v2/?" ;
        httpRequestParam.apiType = HttpRequestParam.ApiType.GetAddr;
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("coordtype", "gcj02ll");
       // httpRequestParam.paramMap.put("callback", "renderReverse");
        String location=String.valueOf(lat)+","+String.valueOf(lng);
        httpRequestParam.paramMap.put("location", location);
        httpRequestParam.paramMap.put("output", "json");
     //   httpRequestParam.paramMap.put("pois", "1");
        httpRequestParam.paramMap.put("ak", "DeIyUODYbDTmChNw4cuzy0sog8qz3DlI");
        httpRequestParam.abstractBasicActivity=abstractBasicActivity;
        bulidDefaultTask(httpRequestParam);
    }

    public static void getAddr(AbstractService abstractService, double lat, double lng) {
        //HttpRequestParam httpRequestParam = bulidHttpRequestParam("route/uploadData?", HttpRequestParam.ApiType.ReUPGps);
        HttpRequestParam httpRequestParam = new HttpRequestParam();
        httpRequestParam.url = "http://api.map.baidu.com/geocoder/v2/?" ;
        httpRequestParam.apiType = HttpRequestParam.ApiType.GetAddr;
        httpRequestParam.requestType = HttpRequestParam.RequestType.Get;
        httpRequestParam.paramMap.put("coordtype", "gcj02ll");
        // httpRequestParam.paramMap.put("callback", "renderReverse");
        String location=String.valueOf(lat)+","+String.valueOf(lng);
        httpRequestParam.paramMap.put("location", location);
        httpRequestParam.paramMap.put("output", "json");
        //   httpRequestParam.paramMap.put("pois", "1");
        httpRequestParam.paramMap.put("ak", "DeIyUODYbDTmChNw4cuzy0sog8qz3DlI");
        httpRequestParam.abstractService=abstractService;
        bulidDefaultTask(httpRequestParam);
    }
    //*************************************************************************************************************************
    private static HttpRequestParam bulidHttpRequestParam(String path, HttpRequestParam.ApiType apiType) {
        HttpRequestParam httpRequestParam = new HttpRequestParam();
        httpRequestParam.url = url + path;
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
        try {
           // StringUnit.println(tag,requestConfig.abstractBasicActivity.getLocalClassName());
            StringUnit.println(tag,requestConfig.abstractBasicActivity.getAbstratorHandler().getClass().getName());
        }catch (Exception e){}
        MainBackGroundTask mAuthTask = new MainBackGroundTask();
        mAuthTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestConfig);
    }

    private static void bulidExecutorTask(HttpRequestParam requestConfig) {
        MainBackGroundTask mAuthTask = new MainBackGroundTask();
        mAuthTask.executeOnExecutor(executor, requestConfig);
    }

    public static ResponeInfo executeHttp(HttpRequestParam... params) {
        StringUnit.println(tag,"executeHttp");
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
        responeInfo.abstractService=param.abstractService;
        return responeInfo;
    }


}
