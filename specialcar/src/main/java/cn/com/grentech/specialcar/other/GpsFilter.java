package cn.com.grentech.specialcar.other;

import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.specialcar.entity.GpsInfo;

import static android.R.attr.angle;
import static cn.com.grentech.specialcar.service.ServiceGPS.minDis;
import static cn.com.grentech.specialcar.service.ServiceGPS.speedLimit;

/**
 * Created by Administrator on 2017/6/20.
 */

public class GpsFilter {
    private List<GpsInfo> list=new ArrayList<>();

    public GpsInfo addGpsInfoFilter(GpsInfo object)
    {
        if(list.size()>=5)
        {
            list.remove(0);
        }
        list.add(object);
        if(list.size()>=2)
        {
            int size=list.size();
            GpsInfo first=list.get(size-2);
            GpsInfo second=list.get(size-1);

        }

        GpsInfo last=null;
        for (GpsInfo info:list) {
            if(last==null)
            {
                last=info;
            }else
            {

            }
        }

        return null;

    }

    private final static double EARTH_RADIUS = 6378137.0;
    /**
     * 经纬度点 距离计算
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return 返加  米 单位
     */
    public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        //s=s/1000;
        return s;
    }


    public static double gps2d(double lat_a, double lng_a, double lat_b, double lng_b) {
        double d = 0;
        lat_a=lat_a*Math.PI/180;
        lng_a=lng_a*Math.PI/180;
        lat_b=lat_b*Math.PI/180;
        lng_b=lng_b*Math.PI/180;

        d=Math.sin(lat_a)*Math.sin(lat_b)+Math.cos(lat_a)*Math.cos(lat_b)*Math.cos(lng_b-lng_a);
        d=Math.sqrt(1-d*d);
        d=Math.cos(lat_b)*Math.sin(lng_b-lng_a)/d;
        d=Math.asin(d)*180/Math.PI;
        return d;
    }

    public static double gpsAngle(GpsInfo first,GpsInfo second)
    {
        if(first==null)return 0;
        double angle=gps2d(first.getLat(),first.getLng(),second.getLat(),second.getLng());
        return angle;
    }

    public static double gpsSpeed(GpsInfo first, GpsInfo second)
    {
        if(first==null)return 0;
        double distance=gps2m(first.getLat(),first.getLng(),second.getLat(),second.getLng());
        if(distance==0)return 0.0;
        double speed = distance/((first.getCreateTime() - second.getCreateTime()) / 1000);
        return Math.abs(speed);
    }

    public static double gpsDistance(GpsInfo first,GpsInfo second)
    {
        if(first==null)return 0;
        double distance=gps2m(first.getLat(),first.getLng(),second.getLat(),second.getLng());

        return Math.abs(distance);
    }



    public static double getMoveDistance(List<GpsInfo> gps) {
        double distance = 0;
        GpsInfo last = null;
        for (GpsInfo e : gps) {
            if (last == null) {
                last = e;
                e.setUseable(true);
            } else {
                double dis = GpsFilter.gpsDistance(last, e);
                double speed = GpsFilter.gpsSpeed(last, e);
                if (dis <= minDis || speed >= speedLimit) {
                } else {
                    last = e;
                    distance = distance + dis;
                    e.setUseable(true);
                }
            }
        }
        return distance;
    }
}
