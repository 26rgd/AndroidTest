package cn.com.grentech.specialcar.service;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.List;

import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.abstraction.AbstractService;
import cn.com.grentech.specialcar.activity.LoginActivity;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.entity.OrderStatus;
import cn.com.grentech.specialcar.handler.ServiceAddrHandler;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ServiceAddr extends AbstractService implements LocationListener {
    private static AbstractHandler abstratorHandler = null;
    private LocationManager locationManager;
    private String locationProvider;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StringUnit.println(tag, "Start ServiceAddr method...........");
        if (abstratorHandler == null) abstratorHandler = new ServiceAddrHandler(null);
        try {
            starGPS();
        }catch (Exception e){}

        return super.onStartCommand(intent, START_STICKY, startId);

    }


    @Override
    public void onDestroy() {
        try {
            stopGps();
        }catch (Exception e){}

        StringUnit.println(tag, "STOP ServiceAddr method...........");
        super.onDestroy();
    }


    private void starGPS() {
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                locationProvider = locationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else {
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(locationProvider.trim(), 120000, 500, this);
            Location location = locationManager.getLastKnownLocation(locationProvider);
            onLocationChanged(location);
        }
    }

    private void stopGps() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                return;
            }
            locationManager.removeUpdates(this);
            locationManager = null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            HttpRequestTask.getAddr(this, location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
       System.out.print("onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public AbstractHandler getAbstratorHandler() {
        return abstratorHandler;
    }
}
