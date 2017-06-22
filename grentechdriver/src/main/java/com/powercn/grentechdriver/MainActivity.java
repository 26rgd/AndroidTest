package com.powercn.grentechdriver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.powercn.grentechdriver.activity.AbstractBasicActivity;
import com.powercn.grentechdriver.activity.mainmap.AbstractChildView;
import com.powercn.grentechdriver.activity.mainmap.DeteTimeView;
import com.powercn.grentechdriver.activity.mainmap.UserInfoView;
import com.powercn.grentechdriver.common.http.HttpRequestTask;
import com.powercn.grentechdriver.common.unit.GsonUnit;
import com.powercn.grentechdriver.common.unit.StringUnit;
import com.powercn.grentechdriver.common.unit.ViewUnit;
import com.powercn.grentechdriver.common.websocket.WebSocketTask;
import com.powercn.grentechdriver.entity.AddressInfo;
import com.powercn.grentechdriver.entity.LoginInfo;
import com.powercn.grentechdriver.entity.OrderInfo;
import com.powercn.grentechdriver.entity.ResponseUerInfo;
import com.powercn.grentechdriver.handle.MainMessageHandler;

import org.java_websocket.drafts.Draft_17;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;


/**
 * Created by Administrator on 2017/5/8.
 */

public class MainActivity extends AbstractBasicActivity {
    private MapView mapView = null;
    private AMap aMap = null;

    public DeteTimeView deteTimeView;

    public UserInfoView userInfoView;



    public String deviceuuid;
    public LoginInfo loginInfo;
    public AddressInfo home;
    public AddressInfo company;
    public ResponseUerInfo responseUerInfo = new ResponseUerInfo();
    public static MainMessageHandler mainMessageHandler = null;

    public static String phone = "13800138000";

    public String headpath = "";
    public String tempfile = "temp.jpg";
    public String tempShootfile = "tempshoot.jpg";
    public String upfile = "tempup.jpg";
    public Bitmap upBitmap;
    public String cityCode = "0755";
    public String cityName = "深圳市";

    public final static int otherCode = 0xFFFF;
    WebSocketTask c = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        onOpen();
        sendWebSocket("");
        autoLogin();


    }
    private void onOpen() {
        try {
            // WebSocketTask c = new WebSocketTask( new URI( "ws://192.168.5.42:8080/grentechdriverWx/webSocketServer" ), new Draft_17() );
            c = new WebSocketTask(new URI("ws://192.168.5.25/grentechTaxiWx/webSocketServer"), new Draft_17());
            c.connectBlocking();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWebSocket(String msg) {
        try {
            c.send("13510197040");
            c.send("测试--handshake");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void autoLogin() {
        loginInfo = LoginInfo.readUserLoginInfo(this);
        if (loginInfo.doLoginSuccess) {
            HttpRequestTask.loginByUuid(loginInfo.phone, deviceuuid);
        }
    }

    @Override
    protected void initView() {
        mapView = (MapView) findViewById(R.id.map_mainmap_gaode);
        ViewUnit.setWindowStatusBarColor(this, R.color.MainMapTitleBackColor);
    }

    @Override
    protected void bindListener() {
    }

    @Override
    protected void initData() {

        headpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ViewUnit.getResString(this, R.string.filedir)+"/";
        File file = new File(headpath);
        if (!file.exists()) {
            file.mkdirs();
        }
        StringUnit.println(headpath);
        mainMessageHandler = new MainMessageHandler(this);
        deviceuuid = LoginInfo.getUuid(this);
        home = AddressInfo.readHome(this);
        company = AddressInfo.readCompany(this);
        deteTimeView = new DeteTimeView(this, R.id.layout_datetime);

        userInfoView = new UserInfoView(this, R.id.layout_userinfo);


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
                            savefileup(head,upfile);
                            HttpRequestTask.headUplod(loginInfo.phone, headpath + upfile);
                            upBitmap = head;
                        }
                    }
                    break;
                case otherCode:
                    loginInfo = LoginInfo.readUserLoginInfo(this);
                    StringUnit.println(GsonUnit.toJson(loginInfo));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void savefileup(Bitmap bitmap,String path) {
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
            e.printStackTrace();
        }

    }

    public void savefile(Bitmap bitmap,String path) {
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
            e.printStackTrace();
        }

    }








    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    public static void sendHandleMessage(String key, String content, Object object) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(key, content);
            Message msg = new Message();
            msg.what = 0;
            msg.setData(bundle);
            msg.obj = object;
            mainMessageHandler.sendMessage(msg);
        } catch (Exception e) {
        }
    }




    public void jumpMianMapView(AbstractChildView abstractChildView) {
        abstractChildView.setVisibility(View.GONE);

    }

    public Bitmap readHeadImage() {
        String filename=loginInfo.bitmapPath;
        Bitmap bitmap=null;
        try {
             bitmap = BitmapFactory.decodeFile(headpath + filename);
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head33);
            }
        }catch (Exception e)
        {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head33);
        }

        return bitmap;
    }


}
