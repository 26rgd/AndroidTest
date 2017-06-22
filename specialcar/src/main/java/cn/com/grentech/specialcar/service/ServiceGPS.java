package cn.com.grentech.specialcar.service;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractService;
import cn.com.grentech.specialcar.activity.LoginActivity;
import cn.com.grentech.specialcar.activity.MainActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.unit.FileUnit;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.GpsInfo;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.entity.OrderStatus;
import cn.com.grentech.specialcar.entity.Route;
import cn.com.grentech.specialcar.entity.RouteGpsInfo;
import cn.com.grentech.specialcar.other.GpsFilter;

import static android.R.attr.order;
import static android.app.Service.START_REDELIVER_INTENT;
import static cn.com.grentech.specialcar.entity.GpsInfo.bulidGpsInfo;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ServiceGPS extends AbstractService implements LocationListener {
    private LocationManager locationManager;
    private String locationProvider;

    public final static int speedLimit = 38;
    public final static int minDis = 20;
    private Boolean isFisrt = true;
    private Order info;

    private List<GpsInfo> gps = new ArrayList<GpsInfo>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StringUnit.println(tag, "ServiceGPS|" + Process.myPid());
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Order curOrder = (Order) bundle.getSerializable("GPS");
                if (curOrder != null)
                    info = (Order) bundle.getSerializable("GPS");
                else
                    StringUnit.println(tag, "order is null");
            }
        }
        if (info == null) info = LoginInfo.readProcessOrder(this.getApplicationContext());

        if (info != null) {
            StringUnit.println(tag, OrderStatus.values()[info.getFlag()].getName());
            LoginInfo.saveProcessOrder(this.getApplicationContext(), info);
            try {
                if (info.getFlag() == OrderStatus.RunOrder.getValue())
                    starGPS();
                else
                    stopGps();
            } catch (Exception e) {
                StringUnit.println(tag, "GPS启动或者停止有问题");
            }
        }

//        startForeground(-1984,new Notification());
        return super.onStartCommand(intent, START_STICKY, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        showLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }


    private void stopGps() {
        if (locationManager != null) {
            StringUnit.println(tag, "stopGps");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                return;
            }
            locationManager.removeUpdates(this);

        }
    }

    private void starGPS() {
        if (locationManager == null) {
            StringUnit.println(tag, "starGPS");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                locationProvider = locationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else {
                showToast("无可用定位源");
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                return;
            }
            showToast(locationProvider.trim());
            locationManager.requestLocationUpdates(locationProvider.trim(), 4000, 0, this);
        }

    }

    private void showLocation(Location location) {
        if (location == null) return;
        if (isFisrt) {//丢弃第一包
            isFisrt = false;
            return;
        }

        showToast(location.getLatitude() + "|" + location.getLongitude());
        GpsInfo last = null;
        GpsInfo gi = bulidGpsInfo(location);
        int size = gps.size();
        if (size > 0) {
            last = gps.get(size - 1);
        } else {
            gps.add(gi);
            FileUnit.writeAppDataFile(this.getApplicationContext(), info.getId() + ".dat", GsonUnit.toJson(gi), Context.MODE_APPEND);
        }
        double dis = GpsFilter.gpsDistance(last, gi);
        double speed = GpsFilter.gpsSpeed(last, gi);
        double angle = GpsFilter.gpsAngle(last, gi);
        gi.setSpeed((float) speed);
        gi.setAngle(angle);
        if (dis <= minDis || speed >= speedLimit) {
            // 超138Km 或者小于20米的点丢掉
        } else {
            gps.add(gi);
            FileUnit.writeAppDataFile(this.getApplicationContext(), info.getId() + ".dat", GsonUnit.toJson(gi), Context.MODE_APPEND);
        }
        HttpRequestTask.upGps(null, Route.bulidListJson(RouteGpsInfo.bulid(11, info.getId(), gi)));
        double distance = GpsFilter.getMoveDistance(gps);
    }


}
