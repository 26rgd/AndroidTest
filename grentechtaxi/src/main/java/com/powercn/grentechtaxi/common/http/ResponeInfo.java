package com.powercn.grentechtaxi.common.http;

/**
 * Created by Administrator on 2017/3/16.
 */

public class ResponeInfo {
    private int result;
    private HttpRequestParam.ApiType apiType;
    private String json;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public HttpRequestParam.ApiType getApiType() {
        return apiType;
    }

    public void setApiType(HttpRequestParam.ApiType apiType) {
        this.apiType = apiType;
    }
}
