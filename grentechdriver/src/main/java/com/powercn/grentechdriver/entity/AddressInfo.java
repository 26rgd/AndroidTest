package com.powercn.grentechdriver.entity;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/5/23.
 */

public class AddressInfo  implements Serializable {

    private static final long serialVersionUID = 3386968249063309318L;
     public String name;
     public String detailAddr;
     public Float lat;
     public Float lng;
    public double dlat;
    public double dlng;
    public void init(String name,double lat,double lng)
    {
        this.name=name;
        this.lat=(float)lat;
        this.lng=(float)lng;
        this.dlat=lat;
        this.dlng=lng;
    }
    public void init(String name,String detailAddr,double lat,double lng)
    {
        this.name=name;
        this.detailAddr=detailAddr;
        this.lat=(float)lat;
        this.lng=(float)lng;
        this.dlat=lat;
        this.dlng=lng;
    }
    public static void saveHome(Context context, AddressInfo addressInfo) {
        SharedPreferences.Editor editor = context.getSharedPreferences("AddressInfoHome", MODE_PRIVATE).edit();
        editor.putString("name", addressInfo.name);
        editor.putString("detailAddr", addressInfo.detailAddr);
        editor.putFloat("lat", addressInfo.lat);
        editor.putFloat("lng", addressInfo.lng);
        editor.commit();
    }


    public static AddressInfo readHome(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AddressInfoHome", MODE_PRIVATE);
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.name=sharedPreferences.getString("name", "");
        addressInfo.detailAddr=sharedPreferences.getString("detailAddr", "");
        addressInfo.lat=sharedPreferences.getFloat("lat", 0);
        addressInfo.lng=sharedPreferences.getFloat("lng", 0);
        return addressInfo;
    }


    public static void saveCompany(Context context, AddressInfo addressInfo) {
        SharedPreferences.Editor editor = context.getSharedPreferences("AddressInfoCompany", MODE_PRIVATE).edit();
        editor.putString("name", addressInfo.name);
        editor.putString("detailAddr", addressInfo.detailAddr);
        editor.putFloat("lat", addressInfo.lat);
        editor.putFloat("lng", addressInfo.lng);
        editor.commit();
    }


    public static AddressInfo readCompany(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AddressInfoCompany", MODE_PRIVATE);
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.name=sharedPreferences.getString("name", "");
        addressInfo.detailAddr=sharedPreferences.getString("detailAddr", "");
        addressInfo.lat=sharedPreferences.getFloat("lat", 0);
        addressInfo.lng=sharedPreferences.getFloat("lng", 0);
        return addressInfo;
    }
}
