package com.powercn.grentechdriver.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.activity.mainmap.AbstractChildView;
import com.powercn.grentechdriver.activity.mainmap.UserInfoView;
import com.powercn.grentechdriver.adapter.MineAdapter;
import com.powercn.grentechdriver.common.http.HttpRequestTask;
import com.powercn.grentechdriver.common.unit.ErrorUnit;
import com.powercn.grentechdriver.common.unit.ViewUnit;
import com.powercn.grentechdriver.common.websocket.WebSocketTask;
import com.powercn.grentechdriver.entity.LoginInfo;
import com.powercn.grentechdriver.entity.ResponseUerInfo;
import com.powercn.grentechdriver.handle.GlobalHandler;
import com.powercn.grentechdriver.view.CircleImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * Created by Administrator on 2017/5/8.
 */

public class MainActivity extends AbstractBasicActivity {

    public UserInfoView userInfoView;

    private DrawerLayout drawerLayout;

    public String deviceuuid;
    public LoginInfo loginInfo;

    public ResponseUerInfo responseUerInfo = new ResponseUerInfo();
    public String headpath = "";
    public String tempfile = "temp.jpg";
    public String tempShootfile = "tempshoot.jpg";
    public String upfile = "tempup.jpg";
    public Bitmap upBitmap;
    private CircleImageView circleImageView;
    private ListView leftMenu;
    private MineAdapter leftAdapter;

    WebSocketTask c = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        autoLogin();
    }


    private void autoLogin() {
        loginInfo = LoginInfo.readUserLoginInfo(this);
        if (loginInfo.doLoginSuccess) {
            HttpRequestTask.loginByUuid(this,loginInfo.phone, deviceuuid);
        }
    }

    @Override
    protected void initView() {
        ViewUnit.setWindowStatusBarColor(this, R.color.MainMapTitleBackColor);
        circleImageView = (CircleImageView) findViewById(R.id.civ_mainmap_titlebar_account);
        leftMenu=(ListView)findViewById(R.id.lv_leftmenu);
    }

    @Override
    protected void bindListener() {
        circleImageView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initDrawer();
        leftAdapter=new MineAdapter(this,null,R.layout.mainside_popupwindow_item);
        leftMenu.setAdapter(leftAdapter);
    }

    /**
     * 初始化DrawerLayout
     */
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
            switch (requestCode) {
                case 1:
                    this.userInfoView.cropPhoto(data.getData());
                    break;
                case 2:
                    File temp = new File(headpath + this.tempShootfile);
                    this.userInfoView.cropPhotoshoot(Uri.fromFile(temp));
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        Bitmap head = null;// 头像Bitmap
                        head = BitmapFactory.decodeStream(getContentResolver().openInputStream(this.userInfoView.getUritempFile()));
                        if (head != null) {
                            savefileup(head, upfile);
                            HttpRequestTask.headUplod(loginInfo.phone, headpath + upfile);
                            upBitmap = head;
                        }
                    }
                    break;
//                case otherCode:
//                    loginInfo = LoginInfo.readUserLoginInfo(this);
//                    StringUnit.println(tag,GsonUnit.toJson(loginInfo));
//                    break;
            }
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void savefileup(Bitmap bitmap, String path) {
        try {
            File file = new File(headpath + path);
            if (file.exists() == false) {

                file.createNewFile();
            }
            OutputStream inputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, inputStream);
            inputStream.flush();
            inputStream.close();
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        }

    }

    public void savefile(Bitmap bitmap, String path) {
        try {
            File file = new File(headpath + path);
            if (file.exists() == false) {

                file.createNewFile();
            }
            OutputStream inputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, inputStream);
            inputStream.flush();
            inputStream.close();
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_mainmap_titlebar_account:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
    }


    @Override
    public AbstratorHandler getAbstratorHandler() {
        return GlobalHandler.getInstance();
    }


    public void jumpMianMapView(AbstractChildView abstractChildView) {
        abstractChildView.setVisibility(View.GONE);

    }

    public Bitmap readHeadImage() {
        String filename = loginInfo.bitmapPath;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(headpath + filename);
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head33);
            }
        } catch (Exception e) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head33);
        }

        return bitmap;
    }


}
