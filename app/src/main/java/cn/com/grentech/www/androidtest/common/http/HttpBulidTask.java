package cn.com.grentech.www.androidtest.common.http;



import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/1.
 */

public  class HttpBulidTask {


    public static void getDeviceOkList(String sd, String ed) {
        HttpRequestParam httpRequestConfig = new HttpRequestParam();
        httpRequestConfig.httpType=0;
        Map map = new HashMap<String, String>();
        map.put("sd", sd);
        map.put("ed", ed);
        httpRequestConfig.object=(map);
        //HttpRequestTask.bulidTask(0, httpRequestConfig);
    }


}
