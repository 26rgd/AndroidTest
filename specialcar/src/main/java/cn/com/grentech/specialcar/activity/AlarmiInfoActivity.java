package cn.com.grentech.specialcar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.common.unit.AlarmUnit;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.WakeLockUnit;

/**
 * Created by Administrator on 2017/7/3.
 */

public class AlarmiInfoActivity extends AbstractBasicActivity {

    private LinearLayout alram;
    private TextView tvAlarm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        super.onCreate(savedInstanceState, R.layout.activity_alarminfo);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(8*1000);
                    finish();
                }catch (Exception e){ErrorUnit.println(tag,e);}
            }
        }).start();
    }
    @Override
    protected void initView() {
        alram=(LinearLayout)findViewById(R.id.layout_alarm);
        tvAlarm=(TextView)findViewById(R.id.tv_alarm);
    }

    @Override
    protected void bindListener() {
        alram.setOnClickListener(this);
        tvAlarm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        WakeLockUnit.onPower(this);
    }

    @Override
    public void onClick(View v) {
        try{
            AlarmUnit.cancelAlarm();
            finish();
        }catch (Exception e){
            ErrorUnit.println(tag,e);
        }
    }

    /**
     * 设置亮度
     *
     * @param context
     * @param light
     */
    void SetLight(Activity context, int light) {
      //  currentLight = light;
        WindowManager.LayoutParams localLayoutParams = context.getWindow().getAttributes();
        localLayoutParams.screenBrightness = (light / 255.0F);
        context.getWindow().setAttributes(localLayoutParams);
    }

    /**
     * 获取亮度
     *
     * @param context
     * @return
     */
    float GetLightness(Activity context) {
        WindowManager.LayoutParams localLayoutParams = context.getWindow().getAttributes();
        float light = localLayoutParams.screenBrightness;
        return light;
    }


    @Override
    public AbstractHandler getAbstratorHandler() {
        return null;
    }
}
