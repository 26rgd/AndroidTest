package cn.com.grentech.www.androidtest.common.http;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2017/3/17.
 */

public class HttpRequestParam {
    public int httpType;
    public Object object;
    public String url;
    public UrlParams urlParams;

    public void addParams(String key,String value)
    {
        this.urlParams.addParams(key,value);
    }
}
