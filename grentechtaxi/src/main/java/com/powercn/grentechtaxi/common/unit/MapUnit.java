package com.powercn.grentechtaxi.common.unit;

import java.util.Map;

import static android.R.attr.key;

/**
 * Created by Administrator on 2017/6/7.
 */

public class MapUnit {

    public static  String toString(Map map,String key) {
        Object object =  map.get(key);
        if(object==null)
            return null;
        else
          return   object.toString();
    }
}
