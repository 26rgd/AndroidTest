package com.powercn.grentechtaxi;

import android.app.Activity;
import android.app.Application;

import com.powercn.grentechtaxi.common.unit.StringUnit;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

public class SysApplication extends Application {
    private String tag=this.getClass().getName();
    private List<Activity> list = new ArrayList<>();
    private static SysApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            x.Ext.init(this);//xutils初始化.....
        }catch (Exception e)
        {
            StringUnit.println(tag,"xutils初始化... Error!!!");
        }

        StringUnit.println(tag,"SysApplication onCreate ******************************");
    }

    public SysApplication() {
        StringUnit.println(tag,"SysApplication ***********************************");
        instance=this;
    }

    public synchronized static SysApplication getInstance() {
        if (null == instance)
            instance = new SysApplication();
        return instance;
    }

    public void addActivity(Activity activity) {
        this.list.add(activity);
    }

    public void exit() {
        for (Activity line : list) {
            if (null != line) {
                try {
                    line.finish();
                } catch (Exception e) {
                }finally {
                }
            }
        }

        System.exit(0);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
