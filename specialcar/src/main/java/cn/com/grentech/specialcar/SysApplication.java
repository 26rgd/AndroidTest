package cn.com.grentech.specialcar;

import android.app.Activity;
import android.app.Application;





import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.specialcar.common.unit.StringUnit;

import static android.R.attr.x;

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
