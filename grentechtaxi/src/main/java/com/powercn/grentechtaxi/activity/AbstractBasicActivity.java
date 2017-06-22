package com.powercn.grentechtaxi.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.SysApplication;
import com.powercn.grentechtaxi.activity.mainmap.AbstractChildView;
import com.powercn.grentechtaxi.activity.mainmap.MainChildView;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.entity.OrderInfo;

import lombok.Getter;

/**
 * Created by Administrator on 2017/4/20.
 */

public abstract class AbstractBasicActivity extends AppCompatActivity implements View.OnClickListener {
    protected String tag=this.getClass().getName();
    private Boolean isExit = false;
    private ImageView homePage;
    protected ConnectionService connectionService;
    @Getter
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

    }


    protected abstract void initView();

    protected abstract void bindListener();

    protected abstract void initData();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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



    protected void jumpMainActivity() {
        if (!(this instanceof MainActivity)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void jumpFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
        StringUnit.println(tag ," jumpFinish");

    }


    public void jumpNotFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
    public void jumpForResult(Class<?> cls,int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent,requestCode);
    }
    public void jumpForResult(Class<?> cls,int requestCode,String key,String content) {
        Intent intent = new Intent(this, cls);
        Bundle bundle=new Bundle();
        bundle.putString(key,content);
        intent.putExtras(bundle);
        startActivityForResult(intent,requestCode);
    }
    public void jumpForResult(Class<?> cls, OrderInfo object, int requestCode) {
        Intent intent = new Intent(this, cls);
        Bundle bundle=new Bundle();
        bundle.putSerializable("object",object);
        intent.putExtras(bundle);
        startActivityForResult(intent,requestCode);
       // startActivity(intent);
    }
    @Override
    public void onClick(View v) {

    }


    public void hideSoft() {
        try {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e)
        {}

    }

    public void showToast(String msg) {
        try {
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    public void showToastLong(String msg) {
        try {
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    public class ConnectionService implements ServiceConnection
    {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
