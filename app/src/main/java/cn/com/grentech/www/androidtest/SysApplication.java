package cn.com.grentech.www.androidtest;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

public class SysApplication extends Application {
    private List<Activity> list = new ArrayList<>();
    private static SysApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("SysApplication onCreate ******************************");
    }

    public SysApplication() {
        System.out.println("SysApplication ***********************************");
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
