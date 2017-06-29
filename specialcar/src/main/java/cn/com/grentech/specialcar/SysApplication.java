package cn.com.grentech.specialcar;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;


import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.FileUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.error.UnError;
import cn.com.grentech.specialcar.service.ServiceAddr;
import lombok.Getter;

import static android.R.attr.x;

/**
 * Created by Administrator on 2017/4/20.
 */

public class SysApplication extends Application {
    private String tag=this.getClass().getName();
    private List<Activity> list = new ArrayList<>();
    @Getter
    private Context context;
    private static SysApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        FileUnit.iniDir(context);
        UnError unError=new UnError();
        StringUnit.println(tag,"SysApplication onCreate ******************************Process Id|"+ Process.myPid());
        try{
            StringUnit.println(tag, "MODEL|"+Build.MODEL);
            StringUnit.println(tag, "SDK_INI|"+Build.VERSION.SDK_INT);
            StringUnit.println(tag, "RELEASE|"+Build.VERSION.RELEASE);
            StringUnit.println(tag, "BRAND|"+Build.BRAND);

        }catch (Exception e)
        {
            ErrorUnit.println(tag,e);
        }
    }

    public SysApplication() {
        super.onCreate();
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

    @Override
    public void onTerminate() {
        StringUnit.println(tag,"application onTerminate");
        super.onTerminate();
    }


    @Override
    public void onTrimMemory(int level)
    {
        StringUnit.println(tag,"application onTrimMemory 进入后台执行");
        super.onTrimMemory(level);
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
        stopService(new Intent(this,ServiceAddr.class));
        StringUnit.println(tag,"application exit()    用户主动退出");
        System.exit(0);
    }

    @Override
    public void onLowMemory() {
        StringUnit.println(tag,"application onLowMemory  ");
        super.onLowMemory();
        System.gc();
    }
}
