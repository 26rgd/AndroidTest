package cn.com.grentech.www.androidtest.common.http;

/**
 * Created by Administrator on 2017/3/16.
 */

public class ResponeInfo {
    private int result;
    private int httpType;
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

    public int getHttpType() {
        return httpType;
    }

    public void setHttpType(int httpType) {
        this.httpType = httpType;
    }
}
