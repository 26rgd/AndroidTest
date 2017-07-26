package cn.com.grentech.specialcar.entity;

import android.location.Location;

import java.io.Serializable;

import cn.com.grentech.specialcar.common.unit.CoordinateSystem;
import cn.com.grentech.specialcar.common.unit.DateUnit;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import lombok.Data;

/**
 * Created by Administrator on 2017/6/20.
 */

@Data
public class GpsInfo implements Serializable {
    private transient String tag=this.getClass().getName();
    private transient static final long serialVersionUID = -3279843637949859479L;

    private double lat;
    private double lng;
    private float speed;
    private double angle;
    private long createTime;
    private float accuracy;
    private transient double distance;
    private transient double predistance;
    private transient Boolean useable=false;

    public GpsInfo(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public static GpsInfo bulidGpsInfo(Location location) {
       CoordinateSystem.CoordGpsInfo gpsInfo= CoordinateSystem.GpsInfo84_To_Gcj02(location.getLatitude(), location.getLongitude());
        GpsInfo gi = new GpsInfo(gpsInfo.lat, gpsInfo.lon);
        gi.setSpeed(location.getSpeed());
        gi.setCreateTime(location.getTime());
        gi.setAngle(location.getAltitude());
        gi.setAccuracy(location.getAccuracy());

        return gi;
    }

    public String toString()
    {
        try {
            return  "Usable:"+lat+"|"+lng+"|"+accuracy+"|"+speed+"|"+createTime+":"+ DateUnit.formatDate(createTime,"yyyy-MM-dd HH:mm:ss");
        }catch (Exception e)
        {
            ErrorUnit.println(tag,e);
            return "toString Error";}

    }
}
