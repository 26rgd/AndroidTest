package cn.com.grentech.www.androidtest.common.unit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/3/15.
 */

public class GsonUnit {
    private static Gson gson = new GsonBuilder().create();

    public static Object toObject(String json, Class bean) {
        Object obj = gson.fromJson(json, bean);
        return obj;

    }

    public static Object toObject(String json, Object key) {

        HashMap<Object, Object> map = (HashMap<Object, Object>) GsonUnit.toObject(json, HashMap.class);
        Object object = map.get(key);
        return object;

    }

    public static Object toMapObject(String json, Object key, Class cls) {

        HashMap<Object, Object> map = (HashMap<Object, Object>) GsonUnit.toObject(json, HashMap.class);
        Object object = map.get(key);
        return toObject(toJson(object), cls);

    }

    public static Object mapToObject(Object map,Class cls)
    {
        String json=toJson(map);
        return  toObject(json,cls);
    }

    public static List toListObject(String json, Object key, Class cls) {
        HashMap<Object, Object> map = (HashMap<Object, Object>) GsonUnit.toObject(json, HashMap.class);
        List<Object> srclist = (List<Object>) map.get(key);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < srclist.size(); i++) {
            Object object = GsonUnit.toObject(GsonUnit.toJson(srclist.get(i)), cls);
            list.add(object);
        }
        return list;
    }

    public static List toListObject(String json,  Class cls) {
        List<Object> srclist = (List<Object>) GsonUnit.toObject(json, List.class);
      //  List<Object> srclist = (List<Object>) map.get(key);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < srclist.size(); i++) {
            Object object = GsonUnit.toObject(GsonUnit.toJson(srclist.get(i)), cls);
            list.add(object);
        }
        return list;
    }


    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
