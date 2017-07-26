package com.powercn.grentechdriver.abstration;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.ConnectionService;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.powercn.grentechdriver.SysApplication;
import com.powercn.grentechdriver.activity.mainmap.AbstractChildView;
import com.powercn.grentechdriver.common.unit.ErrorUnit;
import com.powercn.grentechdriver.common.unit.StringUnit;
import com.powercn.grentechdriver.handle.GlobalHandler;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;



import static android.view.KeyEvent.KEYCODE_HOME;

/**
 * Created by Administrator on 2017/4/20.
 */

public abstract class AbstractBasicActivity extends AppCompatActivity implements View.OnClickListener {
    protected String tag = this.getClass().getName();
    private Boolean isExit = false;
    private ImageView homePage;
    protected ConnectionService connectionService;
    protected AbstractChildView self;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    protected void onCreate(@Nullable Bundle savedInstanceState, int res) {
        super.onCreate(savedInstanceState);

        setContentView(res);
        SysApplication.getInstance().addActivity(this);
        initView();
        bindListener();
        initData();
        GlobalHandler.getInstance().addObejct(tag,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StringUnit.println(tag,"进入前台");
    }

    protected abstract void initView();

    protected abstract void bindListener();

    protected abstract void initData();

    @Override
    protected void onStop() {
       //  StringUnit.println(tag,"进入后台或者切换activity");
        super.onStop();

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KEYCODE_HOME)
        {
            StringUnit.println(tag,"按了HOME键");
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }

    protected void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "准备退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            SysApplication.getInstance().exit();
        }
    }

    public abstract AbstratorHandler getAbstratorHandler();

    public void jumpFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();

    }


    public void jumpNotFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void jumpForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        Bundle bundle = new Bundle();
        bundle.putInt("requestCode", requestCode);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void jumpForResult(Class<?> cls, int requestCode, String key, Serializable content) {
        Intent intent = new Intent(this, cls);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, content);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View v) {
    }

    public void showToast(String msg) {
        try {
            if(!StringUnit.isEmpty(msg))
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }
    public void showToastLength(String msg) {
        try {
            if(!StringUnit.isEmpty(msg))
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }
    public void sendHandleMessage(String key, String content, Object object) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(key, content);
            Message msg = new Message();
            msg.what = 0;
            msg.setData(bundle);
            msg.obj = object;
            Handler handler = getAbstratorHandler();
            handler.sendMessage(msg);
        } catch (Exception e) {
            ErrorUnit.println(tag,e);

        }
    }

    public void startService(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startService(intent);
    }
    public void stopService(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        stopService(intent);
    }



    public void bindService(Class<?> cls) {
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        Intent intent = new Intent(this, cls);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

//    public void startService(Class<?> cls, Order info) {
//        Intent intent = new Intent(this, cls);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("GPS", info);
//        intent.putExtras(bundle);
//        startService(intent);
//    }

}