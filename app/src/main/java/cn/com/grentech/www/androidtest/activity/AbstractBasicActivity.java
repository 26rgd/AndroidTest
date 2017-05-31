package cn.com.grentech.www.androidtest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.com.grentech.www.androidtest.MainActivity;
import cn.com.grentech.www.androidtest.R;
import cn.com.grentech.www.androidtest.SysApplication;

import static android.R.attr.layout_gravity;

/**
 * Created by Administrator on 2017/4/20.
 */

public abstract class AbstractBasicActivity extends AppCompatActivity implements View.OnClickListener{
    private Boolean isExit = false;
    private ImageView homePage;

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
        homePage=(ImageView)findViewById(R.id.iv_titel_homepage);
        if(homePage!=null)
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpMainActivity();
            }
        });
    }



    protected abstract void initView();
    protected abstract void bindListener() ;
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

    protected void jumpFinish(Class<?> cls)
    {
            Intent intent = new Intent(this, cls);
            startActivity(intent);
            finish();
        System.out.println("jumpFinish");

    }


    protected void jumpNotFinish(Class<?> cls)
    {
            Intent intent = new Intent(this, cls);
            startActivity(intent);
    }


    @Override
    public void onClick(View v) {

    }
}
