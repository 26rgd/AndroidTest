package cn.com.grentech.specialcar.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.specialcar.common.unit.GsonUnit;
import lombok.Data;

/**
 * Created by Administrator on 2017/6/16.
 */

@Data
public class Route implements Serializable {
    @Expose
    private static final long serialVersionUID = -7753479641793716342L;
    private int id;
    private int orderId;
    private long time;
    private double lat;
    private double lng;

    public String getJson()
    {
        return  GsonUnit.toJson(this);
    }

    public static String getListJson(List<Route> list)
    {
        return GsonUnit.toJson(list);
    }
    public static String bulidListJson(RouteGpsInfo gpsInfo)
    {
        List<Route> list=new ArrayList<>();
        Route route=new Route();
        route.setId(gpsInfo.getId());
        route.setLat(gpsInfo.getLat());
        route.setLng(gpsInfo.getLng());
        route.setTime(gpsInfo.getCreateTime());
        route.setOrderId(gpsInfo.getOrderId());
        list.add(route);
        return  getListJson(list);
    }
}
