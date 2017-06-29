package cn.com.grentech.specialcar.common.http;

import java.util.HashMap;
import java.util.Map;

import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractService;

import static android.R.attr.order;

/**
 * Created by Administrator on 2017/3/17.
 */

public class HttpRequestParam {
    public RequestType requestType = RequestType.Post;
    public ApiType apiType;
    public Map<String,String> paramMap=new HashMap<>();
    public String url;
    public UrlParams urlParams;
    public AbstractBasicActivity abstractBasicActivity;
    public AbstractService abstractService;

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
        Login("登录", 1),
        GetNotFinsh("未完成订单", 2),
        GetOrderList("历史订单",3),
        logout("退出",4),
        updateDriver("修改司机",5),
        UpFile("上传图片",6),
        checkSession("获取用户信息",7),
        orderFinish("结束订单",8),
        orderUpdateFlag("订单状态更新",9),
        LoadFile("下载图片",10),
        UPGps("上传GPS",11),
        orderPause("暂停订单",12),
        orderContinue("继续订单",13),
        orderStart("开始订单",14),
        ReUPGps("上传GPS",15),
        GetAddr("获取地址",16),
        UpDrviceLocation("司机地址",17)
        ;
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
