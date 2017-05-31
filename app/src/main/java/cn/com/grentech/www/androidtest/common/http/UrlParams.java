package cn.com.grentech.www.androidtest.common.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/14.
 */

public class UrlParams {
    private Map<String, String> map = new HashMap<>();

    public void clear() {
        this.map.clear();
    }

    public void addParams(String key, String value) {
        this.map.put(key, value);
    }

    public void addAllParams(UrlParams urlParams) {
        if (urlParams != null)
            this.map.putAll(urlParams.map);
    }

    public String toString() {
        String result = "";
        for (Map.Entry<String, String> ent : map.entrySet()) {
            result = result + ent.getKey() + "=" + ent.getValue() + "&";
        }
        return result;
    }
}
