package com.powercn.grentechdriver.common.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 */

public class HttpRequestParam {
    public RequestType requestType = RequestType.Post;
    public ApiType apiType;
    public Map<String,String> paramMap=new HashMap<>();
    public String url;
    public UrlParams urlParams;

    public void addParams(String key, String value) {
        this.urlParams.addParams(key, value);
    }

    public enum RequestType {
        Post("Post", 0), Get("Get", 1), PostText("Post", 2),PostFile("Post", 3),LoadFile("POST",4);
        private String methond;
        private int index;

        private RequestType(String methond, int index) {
            this.methond = methond;
            this.index = index;
        }

        public String getMethond() {
            return methond;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum ApiType {
        SendSmsCrc("发送验证码", 0),
        LoginBySmsCrc("短信登录", 1),
        LoginByUuid("uuid登录", 2),
        BookOrder("叫车",3),
        CancleOrder("取消订单",4),
        GetAllOrderByMobile("获取所有订单",5),
        UpFile("上传图片",6),
        GetUserInfo("获取用户信息",7),
        SaveUserInfo("保存用户信息",8),
        evaluate("订单评价",9),
        LoadFile("下载图片",10);
        private String name;
        private int index;
        private ApiType(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }
}
