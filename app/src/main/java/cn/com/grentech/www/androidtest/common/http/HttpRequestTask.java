package cn.com.grentech.www.androidtest.common.http;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/17.
 */

public class HttpRequestTask {
    private String tag=this.getClass().getName();
    //public static String url = "http://192.168.5.169:8080";
    //public static String url = "http://192.168.5.25:8080";
    public static String url="http://210.75.20.143:5025";
    public static String approot = "/anyeye/mobile/";
    public final static Executor executor= Executors.newFixedThreadPool(3);

    public static ResponeInfo listDevice(HttpRequestParam param) {
        String jsp = "device.jsp?";
        HttpUnit httpUnit = new HttpUnit(param.url, null);
        ResponeInfo responeInfo = httpUnit.executePost();
        responeInfo.setHttpType(param.httpType);
        return responeInfo;
    }



    public static ResponeInfo executeHttp(HttpRequestParam... params) {
        ResponeInfo responeInfo = null;
        HttpRequestParam param = params[0];


        return responeInfo;
    }

    public static ResponeInfo onPostHttp(ResponeInfo responeInfo) {
        System.out.println("***************************************************************");
        System.out.println(responeInfo.getJson());
        if (responeInfo.getJson().contains("未登录")) {
            return responeInfo;
        }
        switch (responeInfo.getHttpType()) {

        }
        return responeInfo;
    }


    public static String bulidSqlFilterEquals(String paramName, String key) {
        return paramName + "%20%3D%20" + key;

    }

    public static String bulidSqlFilterLike(String paramName, String key) {

        return paramName + "%20like%20%27%25" + key + "%25%27%20";
    }

    public static String bulidSqlFilterEqualsPost(String paramName, String key) {
        return paramName + "=" + key;

    }

    public static String bulidSqlFilterLikePost(String paramName, String key) {

        return paramName + "= %" + key + "%";
    }

}
