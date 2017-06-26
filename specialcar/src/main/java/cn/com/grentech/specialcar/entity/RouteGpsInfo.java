package cn.com.grentech.specialcar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2017/6/20.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class RouteGpsInfo extends GpsInfo {
    private int id;
    private int orderId;
    public RouteGpsInfo(double lat, double lng) {
        super(lat, lng);
    }

    public static RouteGpsInfo bulid(int orderId,GpsInfo gpsInfo)
    {
        RouteGpsInfo object=new RouteGpsInfo(gpsInfo.getLat(),gpsInfo.getLng());
        object.setId(orderId);
        object.setOrderId(orderId);
        object.setCreateTime(gpsInfo.getCreateTime());
        object.setAngle(gpsInfo.getAngle());
        object.setSpeed(gpsInfo.getSpeed());
        object.setUseable(gpsInfo.getUseable());
        return  object;
    }
}
