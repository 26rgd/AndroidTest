package cn.com.grentech.specialcar.common.unit;


import cn.com.grentech.specialcar.entity.GpsInfo;

/**
 * 各地图API坐标系统比较与转换;
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GpsInfo芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
 * 谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
 * GCJ02坐标系：即火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。由WGS84坐标系经加密后的坐标系。
 * 谷歌中国地图和搜搜中国地图采用的是GCJ02地理坐标系; BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系;
 * 搜狗坐标系、图吧坐标系等，估计也是在GCJ02基础上加密而成的。
 * Mercator投影坐标转换成WGS84坐标
 */
public class CoordinateSystem {

    public static class CoordGpsInfo
    {
        public double lat;
        public double lon;

        public CoordGpsInfo(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }

    public static final String BAIDU_LBS_TYPE = "bd09ll";

    public static double pi = 3.1415926535897932384626;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;


    public static CoordGpsInfo GpsInfoToBaidu(double lat, double lon)
    {
        CoordGpsInfo cg=GpsInfo84_To_Gcj02(lat,lon);
        CoordGpsInfo result= gcj02_To_Bd09(cg.lat,cg.lon);
        return result;
    }

    /**
     * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
     *
     * @param lat
     * @param lon
     * @return
     */
    public static CoordGpsInfo GpsInfo84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return null;
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        double[] result=new double[2];
        return new CoordGpsInfo(mgLat, mgLon);
    }

    /**
     * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
     * */
    public static double[] gcj2wgs84(double lat, double lon) {
        double[] retval = transform(lat, lon);
        retval[0] = lon * 2 - retval[0];
        retval[1] = lat * 2 - retval[1];
        return retval;
    }

    public static CoordGpsInfo gcj2ToGpsInfo84(double lat, double lon)
    {
        double[] gps=gcj2wgs84(lat,lon);
        CoordGpsInfo gpsInfo=new CoordGpsInfo(gps[1],gps[0]);
        return gpsInfo;
    }
    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param gg_lat
     * @param gg_lon
     */
    public static CoordGpsInfo gcj02_To_Bd09(double gg_lat, double gg_lon) {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new CoordGpsInfo(bd_lat, bd_lon);
    }

//	   public static LatLng convertGPSToBaidu(LatLng srLatLng) {
//	        CoordinateConverter converter = new CoordinateConverter();
//	        converter.from(CoordinateConverter.CoordType.COMMON);
//	        converter.coord(srLatLng);
//	        return converter.convert();
//	    }
//
//	   public static LatLng convertGPSToBaidu(CoordGpsInfo gps) {
//		   LatLng srLatLng=new LatLng(gps.getLat(), gps.getLng());
//	        CoordinateConverter converter = new CoordinateConverter();
//	        converter.from(CoordinateConverter.CoordType.COMMON);
//	        converter.coord(srLatLng);
//	        return converter.convert();
//	    }
	   
//	   public static LatLng convertGPS84ToBaidu(double lat, double lng)
//	   {
//		   CoordGpsInfo gps=CoordinateSystem.GpsInfo84_To_Gcj02(lat,lng);
//		   LatLng point =CoordinateSystem.convertGPSToBaidu(gps);
//		   return point;
//	   }
    /**
     * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
     * bd_lat * @param bd_lon * @return
     */
    public static CoordGpsInfo bd09_To_Gcj02(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new CoordGpsInfo(gg_lat, gg_lon);
    }


//    public static CoordGpsInfo bd09_To_GpsInfo84(double bd_lat, double bd_lon) {
//
//        CoordGpsInfo gcj02 = PositionUtil.bd09_To_Gcj02(bd_lat, bd_lon);
//        CoordGpsInfo map84 = PositionUtil.gcj_To_GpsInfo84(gcj02.getWgLat(),
//                gcj02.getWgLon());
//        return map84;
//
//    }

    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }


    private static double[] transform(double lat, double lon) {
//        if (outOfChina(lat, lon)) {
//            return new CoordGpsInfo(lat, lon);
//        }
        double[] retval = new double[2];
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        retval[0] = mgLon;
        retval[1] = mgLat;
        return retval;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
                * pi)) * 2.0 / 3.0;
        return ret;
    }

    //static double M_PI = Math.PI;

    //经纬度转墨卡托
    // 经度(lon)，纬度(lat)
    public static double[] wgs2Mercator(double lon,double lat)
    {
        double[] xy = new double[2];
        double x = lon *20037508.342789/180;
        double y = Math.log(Math.tan((90+lat)*pi/360))/(pi/180);
        y = y *20037508.34789/180;
        xy[0] = x;
        xy[1] = y;
        return xy;
    }

    //墨卡托转经纬度
    public static double[] mercator2wgs(double mercatorX,double mercatorY)
    {
        double[] xy = new double[2];
        double x = mercatorX/20037508.34*180;
        double y = mercatorY/20037508.34*180;
        y= 180/pi*(2*Math.atan(Math.exp(y*pi/180))-pi/2);
        xy[0] = x;
        xy[1] = y;
        return xy;
    }


}
