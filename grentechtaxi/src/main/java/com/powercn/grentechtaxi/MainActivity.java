package com.powercn.grentechtaxi;

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

import com.powercn.grentechtaxi.activity.AbstractBasicActivity;
import com.powercn.grentechtaxi.activity.mainmap.MainChildView;
import com.powercn.grentechtaxi.activity.mainmap.CallActionView;
import com.powercn.grentechtaxi.activity.mainmap.CallCarView;
import com.powercn.grentechtaxi.activity.mainmap.DepartView;
import com.powercn.grentechtaxi.activity.mainmap.DestinationView;
import com.powercn.grentechtaxi.activity.mainmap.DeteTimeView;
import com.powercn.grentechtaxi.activity.mainmap.HomeCompanyView;
import com.powercn.grentechtaxi.activity.mainmap.MainMapView;
import com.powercn.grentechtaxi.activity.mainmap.TripFinshView;
import com.powercn.grentechtaxi.activity.mainmap.TripRunView;
import com.powercn.grentechtaxi.activity.mainmap.TripWaitView;
import com.powercn.grentechtaxi.activity.mainmap.UserInfoView;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.unit.GsonUnit;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.common.unit.ViewUnit;
import com.powercn.grentechtaxi.entity.AddressInfo;
import com.powercn.grentechtaxi.entity.LoginInfo;
import com.powercn.grentechtaxi.entity.OrderInfo;
import com.powercn.grentechtaxi.entity.ResponseUerInfo;
import com.powercn.grentechtaxi.handle.MainMessageHandler;
import com.powercn.grentechtaxi.service.WebSocketService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static com.powercn.grentechtaxi.common.unit.GsonUnit.toObject;
import static com.powercn.grentechtaxi.entity.CallOrderStatusEnum.NOTAXI;


/**
 * Created by Administrator on 2017/5/8.
 */

public class MainActivity extends AbstractBasicActivity {
    private MapView mapView = null;
    private AMap aMap = null;
    //public AMapNavi aMapNavi=null;
    public CallCarView callCarView;
    public CallActionView callActionView;
    public MainMapView mainMapView;
    public DepartView departView;
    public DestinationView destinationView;
    public DeteTimeView deteTimeView;
    public TripRunView tripRunView;
    public TripWaitView tripWaitView;
    public TripFinshView tripFinshView;
//    public TripOrderView tripOrderView;
//    public TripEvaluateView tripEvaluateView;
    public UserInfoView userInfoView;
    public HomeCompanyView homeCompanyView;
//    public OrderDetailView orderDetailView;


    public String deviceuuid;
    public static LoginInfo loginInfo;
    public AddressInfo home;
    public AddressInfo company;
    public ResponseUerInfo responseUerInfo = new ResponseUerInfo();
    public static MainMessageHandler mainMessageHandler = null;


    public OrderInfo orderInfo;
    public AddressInfo startAddr = new AddressInfo();
    public AddressInfo destAddr = new AddressInfo();
    public int totalMileage;
    public int totalMoney;
    public String headpath = "";
    public String tempfile = "temp.jpg";
    public String tempShootfile = "tempshoot.jpg";
    public String upfile = "tempup.jpg";
    public Bitmap upBitmap;
    public String cityCode = "0755";
    public String cityName = "深圳市";


    public final static int otherCode = 0xFFFF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        autoLogin();
        initMap(savedInstanceState);

        //HttpRequestTask.loginByPassword("admin", "loginByPassword");

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

        headpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ViewUnit.getResString(this, R.string.filedir) + "/";
        File file = new File(headpath);
        if (!file.exists()) {
            file.mkdirs();
        }
        StringUnit.println(tag, headpath);
        mainMessageHandler = new MainMessageHandler(this);
        deviceuuid = LoginInfo.getUuid(this);
        home = AddressInfo.readHome(this);
        company = AddressInfo.readCompany(this);
        callCarView = new CallCarView(this, R.id.layout_mainmap_callcar);
        callActionView = new CallActionView(this, R.id.layout_callaction);
        // loginView = new LoginView(this, R.id.layout_login);
        departView = new DepartView(this, R.id.layout_mainmap_depart);
        destinationView = new DestinationView(this, R.id.layout_mainmap_destination);
        deteTimeView = new DeteTimeView(this, R.id.layout_datetime);
        tripRunView = new TripRunView(this, R.id.layout_trip_run);
        tripWaitView = new TripWaitView(this, R.id.layout_trip_wait);
        tripFinshView = new TripFinshView(this, R.id.layout_trip_finsh);
//        tripOrderView = new TripOrderView(this, R.id.layout_trip_order);
//        tripEvaluateView = new TripEvaluateView(this, R.id.layout_trip_evaluate);
        userInfoView = new UserInfoView(this, R.id.layout_userinfo);
        homeCompanyView = new HomeCompanyView(this, R.id.layout_select_homecompany);
//        orderDetailView = new OrderDetailView(this, R.id.layout_oder_detail);

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
                case otherCode:
                    loginInfo = LoginInfo.readUserLoginInfo(this);
                    mainMapView.getIvHead().setImageBitmap(readHeadImage());
                    StringUnit.println(tag, GsonUnit.toJson(loginInfo));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, inputStream);
            inputStream.flush();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

    }

    private void initMap(Bundle savedInstanceState) {
        try {
            mapView.onCreate(savedInstanceState);
            if (aMap == null) {
                aMap = mapView.getMap();
            }
            mainMapView = new MainMapView(this, R.id.layout_mainmap, mapView, aMap);
            // aMapNavi=AMapNavi.getInstance(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mainMapView.onDestroy();
            mapView.onDestroy();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mapView.onResume();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mapView.onPause();
        } catch (Exception e) {
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


    public void postMessage(final PostType what, Object object) {
        try {

            mainMessageHandler.post(new Runnable() {
                @Override
                public void run() {
                    switch ( what)
                    {
                        case CallAction:
                            //jumpMianMapView(callActionView);
                            showToast(NOTAXI.getName());
                           // mainMapView.setVisibility(View.GONE);
                            tripWaitView.setVisibility(View.VISIBLE);
                            break;
                    }

                }
            });

        } catch (Exception e) {
        }
    }


    public void hideMainView() {
        mainMapView.setVisibility(View.GONE);
    }

    public void jumpMianMapView(MainChildView abstractChildView) {
        abstractChildView.setVisibility(View.GONE);
        mainMapView.setVisibility(View.VISIBLE);
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

    public Bitmap readUserInfoHeadImage() {
        String filename = loginInfo.bitmapPath;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(headpath + filename);
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_head_mine);
            }
        } catch (Exception e) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_head_mine);
        }

        return bitmap;
    }

    public void startService() {
        Intent intent = new Intent(MainActivity.this, WebSocketService.class);
        startService(intent);
    }

    public enum PostType {
        CallAction("叫车");
        private String name;

        private PostType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
