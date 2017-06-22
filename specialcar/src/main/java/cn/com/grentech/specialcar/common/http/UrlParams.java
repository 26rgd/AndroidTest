package cn.com.grentech.specialcar.common.http;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * Created by Administrator on 2017/3/14.
 */

public class UrlParams {


    private Map<String, String> map = new HashMap<>();

    public Map<String, String> getMap() {
        return map;
    }

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
            if(ent.getKey().length()==0)
            {
                return ent.getValue();
            }
        }
        return result;
    }
}
