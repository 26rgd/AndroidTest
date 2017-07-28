package cn.com.grentech.specialcar.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.SysApplication;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.adapter.OrderDetailAdapter;
import cn.com.grentech.specialcar.broadcast.MainBroadcastReceiver;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.http.HttpUnit;
import cn.com.grentech.specialcar.common.unit.AlarmUnit;
import cn.com.grentech.specialcar.common.unit.CoordinateSystem;
import cn.com.grentech.specialcar.common.unit.DialogUtils;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.FileUnit;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.NetworkUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.common.unit.ViewUnit;
import cn.com.grentech.specialcar.common.unit.WakeLockUnit;
import cn.com.grentech.specialcar.entity.GpsInfo;
import cn.com.grentech.specialcar.entity.LoadLine;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.entity.OrderStatus;
import cn.com.grentech.specialcar.entity.Route;
import cn.com.grentech.specialcar.entity.RouteGpsInfo;
import cn.com.grentech.specialcar.handler.OrderDetailMessageHandle;
import cn.com.grentech.specialcar.service.ServiceGPS;
import cn.com.grentech.specialcar.service.ServiceLogin;
import cn.com.grentech.specialcar.service.ServiceMoitor;
import lombok.Getter;

import static android.R.attr.data;
import static android.R.attr.max;
import static android.R.attr.order;
import static android.os.Build.VERSION_CODES.M;
import static android.view.KeyEvent.KEYCODE_HOME;
import static cn.com.grentech.specialcar.R.id.contanier_button_orderdetail;
import static cn.com.grentech.specialcar.activity.EditPasswordActivity.find_password;

/**
 * Created by Administrator on 2017/6/15.
 */

public class OrderDetailActivity extends AbstractBasicActivity {

    public final static int UnFinish_Order = 89;
    private static AbstractHandler abstratorHandler = null;

    private ImageView ivBack;
    private TextView tvTitle;
    private ListView listView;
    private Button btStart;
    @Getter
    private Button btPause;
    private Button btFinish;
    private Button btReUp;
    private LinearLayout contanier;
    @Getter
    private OrderDetailAdapter orderDetailAdapter;
    @Getter
    private Order info;

    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_orderdetail);
        StringUnit.println(tag, "OrderDetailActivity Create Process Id|" + Process.myPid());

    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_title_back);
        tvTitle = (TextView) findViewById(R.id.tv_title_back_hint);
        listView = (ListView) findViewById(R.id.lv_oderdetail);
        btStart = (Button) findViewById(R.id.bt_order_start);
        btPause = (Button) findViewById(R.id.bt_order_pause);
        btFinish = (Button) findViewById(R.id.bt_order_finish);
        btReUp = (Button) findViewById(R.id.bt_order_reUp);
        contanier = (LinearLayout) findViewById(R.id.contanier_button_orderdetail);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        btStart.setOnClickListener(this);
        btPause.setOnClickListener(this);
        btFinish.setOnClickListener(this);
        btReUp.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        registReceiver();
        abstratorHandler = new OrderDetailMessageHandle(this);
        orderDetailAdapter = new OrderDetailAdapter(this, null, R.layout.list_item_orderdetail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String pid = Process.myPid() + "";
        tvTitle.setText("订单详情" + pid);
        if (bundle != null)
            info = (Order) bundle.getSerializable("order");
        if (info == null) {

            info = loadLocalOrder();
            StringUnit.println(tag, "loadLocalOrder|" + GsonUnit.toJson(info));
        }

        if (info != null && (info.getFlag() == OrderStatus.RunOrder.getValue() || info.getFlag() == OrderStatus.PauseOrder.getValue() || info.getFlag() == OrderStatus.NewOrder.getValue())) {
            Double d = queryDistanceTotal();
            btReUp.setVisibility(View.INVISIBLE);
            info.setMileage(d);
            contanier.setVisibility(View.VISIBLE);
            if (info.getFlag() == OrderStatus.NewOrder.getValue()) {
                btStart.setEnabled(true);
                btPause.setEnabled(false);
                btFinish.setEnabled(false);
            }
            if (info.getFlag() == OrderStatus.RunOrder.getValue() || info.getFlag() == OrderStatus.PauseOrder.getValue()) {
                btStart.setEnabled(false);
                btPause.setEnabled(true);
                btFinish.setEnabled(true);
            }
            if (info.getFlag() == OrderStatus.RunOrder.getValue()) {
                starService();
                btPause.setText("暂停");
            }
            if (info.getFlag() == OrderStatus.PauseOrder.getValue()) {
                btPause.setText("继续");
            }

        } else {

        }
        listView.setAdapter(orderDetailAdapter);
        orderDetailAdapter.update(info);
    }

    @Override
    protected void onDestroy() {
        WakeLockUnit.releaseWakeLock();
        unregisterReceiver(broadcastReceiver);
        AlarmUnit.cancelAlarm();
        super.onDestroy();
    }

    private double queryDistanceTotal() {
        double i = 0;
        try {

            GpsInfo gpsInfo = SysApplication.getInstance().getSqLiteHelper().getMaxCreateTimeGPSInfo(info.getId());
            if (gpsInfo != null)
                i = gpsInfo.getDistance();

            return Math.max(i, info.getMileage());
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
            return i;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
//                finish();
//                jumpFinish(MainActivity.class);
                checkStatus();
                break;

            case R.id.bt_order_start:
                HttpRequestTask.orderStart(this, info.getId());

                break;

            case R.id.bt_order_pause:
                pause();
                break;

            case R.id.bt_order_finish:
                StringUnit.println(tag, "click finish!!");
                showToastLength("正在结束订单请稍等.....");

                HttpRequestTask.upGps(this, Route.bulidListJson(info, getListGps()));
                Double d = this.getOrderDetailAdapter().getMileage();
                HttpRequestTask.orderFinish(this, info.getId(), d, "F" + ViewUnit.getVersionCode(this.getApplicationContext()), OrderStatus.FinishOrder.getValue());
                break;

        }
    }

    private List<GpsInfo> getListGps() {
        if (info != null)
            return SysApplication.getInstance().getSqLiteHelper().getGpsInfoList(info.getId());
        else
            return new ArrayList<>();

    }

    private Order loadLocalOrder() {
        return LoginInfo.readProcessOrder(this.getApplicationContext());
    }

    private void pause() {
        if (OrderStatus.PauseOrder.getValue() == this.orderDetailAdapter.getMflag()) {
            StringUnit.println(tag, "click ReStart!!");
            HttpRequestTask.orderContinue(this, info.getId());
        } else {
            StringUnit.println(tag, "click Pause!!");
            HttpRequestTask.orderPause(this, info.getId(), this.orderDetailAdapter.getMileage());
        }
    }

    public void doStartResult() {
        btStart.setEnabled(false);
        btPause.setEnabled(true);
        btFinish.setEnabled(true);
        info.setFlag(OrderStatus.RunOrder.getValue());
        getOrderDetailAdapter().upDateFlag(0, OrderStatus.RunOrder.getValue());
        starService();
    }

    public void doContinueResult() {
        btStart.setEnabled(false);
        btPause.setEnabled(true);
        btFinish.setEnabled(true);
        info.setFlag(OrderStatus.RunOrder.getValue());
        getBtPause().setText("暂停");
        getOrderDetailAdapter().upDateFlag(0, OrderStatus.RunOrder.getValue());
        starService();
    }

    public void doPauseResult() {
        getBtPause().setText("继续");
        info.setFlag(OrderStatus.PauseOrder.getValue());
        getOrderDetailAdapter().upDateFlag(0, OrderStatus.PauseOrder.getValue());
        stopService();
    }

    public void doFinishResult() {
        info.setFlag(OrderStatus.FinishOrder.getValue());
        stopService();
        jumpFinish(MainActivity.class);
    }


    private void starService() {
        startService(ServiceGPS.class, info);
        startService(ServiceMoitor.class);
        startService(ServiceLogin.class);
    }

    private void stopService() {
        startService(ServiceGPS.class, info);
        stopService(ServiceGPS.class);
        stopService(ServiceMoitor.class);
        stopService(ServiceLogin.class);
    }

//    private LoadLine readLoadLine(Order o) {
//        LoadLine loadLine = null;
//        try {
//            loadLine = (LoadLine) FileUnit.readSeriallizable(LoadLine.class.getSimpleName() + info.getId());
//        } catch (Exception e) {
//            try {
//                loadLine = (LoadLine) FileUnit.readSeriallizable(LoadLine.class.getSimpleName());
//                if (loadLine.getOrderinfo().getId() != o.getId()) {
//                    return null;
//                }
//            } catch (Exception e1) {
//                ErrorUnit.println(tag, e1);
//            }
//            ErrorUnit.println(tag, e);
//        }
//
//        return loadLine;
//    }

    private void registReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    StringUnit.println(tag, intent.getAction());
                    // AlarmUnit.startAlarm(OrderDetailActivity.this.getApplicationContext(),AlarmiInfoActivity.class);
                    AlarmUnit.startAlarm(OrderDetailActivity.this.getApplicationContext(), AlarmiInfoActivity.class, 4 * 60 * 1000);
                    return;
                }
                if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    AlarmUnit.cancelAlarm();
                    StringUnit.println(tag, intent.getAction());
                    return;
                }
                if (intent.getAction().equals(MainBroadcastReceiver.action_Addr)) {
                    try {
                        StringUnit.println(tag, intent.getAction());
                        GpsInfo gpsInfo = (GpsInfo) intent.getExtras().getSerializable(MainBroadcastReceiver.action_Addr_key);
                        if (gpsInfo != null) {
                            //  CoordinateSystem.CoordGpsInfo cg=CoordinateSystem.GpsInfoToBaidu(gpsInfo.getLat(), gpsInfo.getLng());
                            HttpRequestTask.getAddr(OrderDetailActivity.this, gpsInfo.getLat(), gpsInfo.getLng());
                        }
                    } catch (Exception e) {
                        ErrorUnit.println(tag, e);
                    }
                    return;
                }
                if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = conn.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isAvailable()) {
                        String name = networkInfo.getTypeName();
                        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        NetworkUnit.ping();
                                    } catch (Exception e) {
                                        ErrorUnit.println(tag, e);
                                    }
                                }
                            }).start();
                            StringUnit.println(tag, "WIFI网络|" + name);
                            WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                            WifiInfo wifiInfo = wifi.getConnectionInfo();
                            StringUnit.println(tag, "WIFI名称|" + wifiInfo.getSSID() + wifi.getWifiState());

                        } else if (networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {

                            StringUnit.println(tag, "有线网络|" + name);
                        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            StringUnit.println(tag, "3G或者4G网络|" + name);
                        } else {
                            StringUnit.println(tag, "其它网络|" + name + ":" + networkInfo.getType());
                        }


                    } else {
                        StringUnit.println(tag, "网络已经断开");
                    }
                    return;
                }
                Double data = intent.getDoubleExtra(MainBroadcastReceiver.action_GPS_key, 0);
                int orderId = intent.getIntExtra(MainBroadcastReceiver.action_GPS_orderId, 0);
                StringUnit.println(tag, "broadcastReceiver" + data);
                if (data > 0) {
                    info.setMileage(data);
                    OrderDetailActivity.this.getOrderDetailAdapter().upDateMileage(orderId, data);
                }
            }
        };
        // 注册一个broadCastReceiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainBroadcastReceiver.action_GPS);
        intentFilter.addAction(MainBroadcastReceiver.action_Addr);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, intentFilter);
//        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
//        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_HOME) {
            StringUnit.println(tag, "按了HOME键");
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            jumpFinish(MainActivity.class);
            checkStatus();
        }
        return false;
    }

    /**
     * 在返回时检查是否需要提醒
     */
    private void checkStatus() {
        if (info.getFlag() == OrderStatus.RunOrder.getValue()) {
            AlertDialog dialog = DialogUtils.getAlert(OrderDetailActivity.this, "退出",
                    "当前订单为执行状态，请先暂停或者结束订单再退出？", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  exit = true;
                            //  continousWrite.write("在订单处理过程中退出订单处理界面！");
                        }
                    });
            dialog.show();
        } else {
            jumpFinish(MainActivity.class);
            finish();
        }
    }

    @Override
    public AbstractHandler getAbstratorHandler() {
        return abstratorHandler;
    }

}
