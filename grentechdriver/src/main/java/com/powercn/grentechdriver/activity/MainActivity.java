package com.powercn.grentechdriver.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.adapter.MineAdapter;
import com.powercn.grentechdriver.common.http.HttpRequestTask;
import com.powercn.grentechdriver.common.unit.DateUnit;
import com.powercn.grentechdriver.common.unit.ErrorUnit;
import com.powercn.grentechdriver.common.unit.ViewUnit;
import com.powercn.grentechdriver.common.websocket.WebSocketTask;
import com.powercn.grentechdriver.entity.LoginInfo;
import com.powercn.grentechdriver.entity.ResponseUerInfo;
import com.powercn.grentechdriver.handle.GlobalHandler;
import com.powercn.grentechdriver.view.CircleImageView;
import com.powercn.grentechdriver.view.MainItemView;
import com.powercn.grentechdriver.view.UpTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static com.powercn.grentechdriver.R.id.tv_main_datetime;


/**
 * Created by Administrator on 2017/5/8.
 */

public class MainActivity extends AbstractBasicActivity {


    public String deviceuuid;
    public LoginInfo loginInfo;

    public ResponseUerInfo responseUerInfo = new ResponseUerInfo();
    public String headpath = "";
    public String tempShootfile = "tempshoot.jpg";
    public String upfile = "tempup.jpg";
    public Bitmap upBitmap;


    private DrawerLayout drawerLayout;
    private CircleImageView circleImageView;
    private TextView tvDateTime;
    private ListView leftMenu;
    private MineAdapter leftAdapter;
    private UpTextView upTextView1;
    private UpTextView upTextView2;
    private UpTextView upTextView3;
    private MainItemView mainItemView1;
    private MainItemView mainItemView2;

    private Button btListen;
    private TextView tvListenHint;

    private Boolean isListenOrder = false;
    WebSocketTask c = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        autoLogin();
        initStatus();
    }


    private void autoLogin() {
        loginInfo = LoginInfo.readUserLoginInfo(this);
        if (loginInfo.doLoginSuccess) {
            HttpRequestTask.loginByUuid(this, loginInfo.phone, deviceuuid);
        }

    }

    @Override
    protected void initView() {
        ViewUnit.setWindowStatusBarColor(this, R.color.MainMapTitleBackColor);
        circleImageView = (CircleImageView) findViewById(R.id.lv_main_head);
        tvListenHint = (TextView) findViewById(R.id.tv_main_listen);
        btListen = (Button) findViewById(R.id.bt_main_listen);
        tvDateTime = (TextView) findViewById(R.id.tv_main_datetime);
        tvDateTime.setText(DateUnit.getDisplayNow());
        leftMenu = (ListView) findViewById(R.id.lv_leftmenu);
        upTextView1 = new UpTextView(this, R.id.uv_main1);
        upTextView1.initLine1(236 + "", R.dimen.textSizeAnswer1, R.color.textColorMain1);
        upTextView1.initLine2("今日流水(元)", R.dimen.textSizeCallCar, R.color.textColorMain2);

        upTextView2 = new UpTextView(this, R.id.uv_main2);
        upTextView2.initLine1(5.0 + "", R.dimen.textSizeAnswer, R.color.textColorMain1);
        upTextView2.initLine2("我的评分", R.dimen.textSizeCallCar, R.color.textColorMain2);

        upTextView3 = new UpTextView(this, R.id.uv_main3);
        upTextView3.initLine1(12 + "", R.dimen.textSizeAnswer, R.color.textColorMain1);
        upTextView3.initLine2("今日单数", R.dimen.textSizeCallCar, R.color.textColorMain2);

        mainItemView1 = new MainItemView(this, R.id.item_view1);

        mainItemView2 = new MainItemView(this, R.id.item_view2);
        mainItemView2.initTitle("我的订单");
        mainItemView2.initIcon(R.drawable.icon_trip_homepage);
    }

    @Override
    protected void bindListener() {
        circleImageView.setOnClickListener(this);
        btListen.setOnClickListener(this);
        mainItemView1.getIvDetail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpForResult(MineActivity.class,1);
            }
        });

        mainItemView1.getTvTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpForResult(MineActivity.class,1);
            }
        });

        mainItemView2.getIvDetail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpForResult(OrderListActivity.class,2);
            }
        });
        mainItemView2.getTvTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpForResult(OrderListActivity.class,2);
            }
        });

    }

    @Override
    protected void initData() {
        initDrawer();
        leftAdapter = new MineAdapter(this, null, R.layout.mainside_popupwindow_item);
        leftMenu.setAdapter(leftAdapter);
        doProcessStatus(isListenOrder);
    }


    private void initDrawer() {
        drawerLayout = ((DrawerLayout) findViewById(R.id.drawer_main));
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R
                .string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lv_main_head:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.bt_main_listen:
                if (!isListenOrder)//在未监控状态下点击就变成监听
                {
                    listenOrder();
                } else {
                    stopListOrder();
                }
                break;
        }
    }

    private void listenOrder() {
        isListenOrder=true;
        doProcessStatus(true);
    }

    private void listenOrderResult(boolean success) {
    }

    private void stopListOrder() {
        isListenOrder=false;
        doProcessStatus(false);
    }

    private void stopListOrderResult(boolean success) {
    }


    private void doProcessStatus(boolean listen)
    {
     if(listen)
     {
         btListen.setText("收车");
         tvListenHint.setVisibility(View.VISIBLE);
     }else
     {
         btListen.setText("出车");
         tvListenHint.setVisibility(View.INVISIBLE);
     }
    }
    private void initStatus()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


            window.setStatusBarColor(0x000000);

            ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
                mChildView.setBackgroundColor(0x000000);
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
        }

    }
    private void initSystemStatus() {
        try {

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
        View statusView = new View(window.getContext());
        int statusHeight = ViewUnit.getStatusBarHeight(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusHeight);
        params.gravity = Gravity.TOP;
        statusView.setLayoutParams(params);
        statusView.setBackgroundResource(R.drawable.bg_homepage);
        viewGroup.addView(statusView);
        } catch (Exception e) {
            ErrorUnit.println(tag,e);
        }
    }

    @Override
    public AbstratorHandler getAbstratorHandler() {
        return GlobalHandler.getInstance();
    }

}
