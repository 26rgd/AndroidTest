package cn.com.grentech.specialcar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.adapter.OrderDetailAdapter;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.entity.OrderStatus;
import cn.com.grentech.specialcar.handler.OrderDetailMessageHandle;
import cn.com.grentech.specialcar.service.ServiceGPS;
import lombok.Getter;

import static android.R.attr.order;
import static cn.com.grentech.specialcar.R.id.contanier_button_orderdetail;
import static cn.com.grentech.specialcar.activity.EditPasswordActivity.find_password;

/**
 * Created by Administrator on 2017/6/15.
 */

public class OrderDetailActivity extends AbstractBasicActivity {
    public final static int Finish_Order = 88;
    public final static int UnFinish_Order = 89;
    private static AbstractHandler abstratorHandler = null;

    private ImageView ivBack;
    private TextView tvTitle;
    private ListView listView;
    private Button btStart;
    @Getter
    private Button btPause;
    private Button btFinish;
    private LinearLayout contanier;
    @Getter
    private OrderDetailAdapter orderDetailAdapter;
    @Getter
    private Order info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_orderdetail);


    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_title_back);
        tvTitle = (TextView) findViewById(R.id.tv_title_back_hint);
        listView = (ListView) findViewById(R.id.lv_oderdetail);
        btStart = (Button) findViewById(R.id.bt_order_start);
        btPause = (Button) findViewById(R.id.bt_order_pause);
        btFinish = (Button) findViewById(R.id.bt_order_finish);
        contanier = (LinearLayout) findViewById(R.id.contanier_button_orderdetail);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        btStart.setOnClickListener(this);
        btPause.setOnClickListener(this);
        btFinish.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        abstratorHandler = new OrderDetailMessageHandle(this);
        orderDetailAdapter = new OrderDetailAdapter(this, null, R.layout.list_item_orderdetail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String pid=Process.myPid()+"";
        tvTitle.setText("订单详情"+ pid);
        info = (Order) bundle.getSerializable("order");
        if (info == null) {
            info = loadLocalOrder();
        }
        if (info != null && (info.getFlag() == OrderStatus.RunOrder.getValue() || info.getFlag() == OrderStatus.PauseOrder.getValue() || info.getFlag() == OrderStatus.NewOrder.getValue())) {
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
                startService(ServiceGPS.class, info);
                btPause.setText("暂停");
            }
            if (info.getFlag() == OrderStatus.PauseOrder.getValue()) {
                btPause.setText("继续");
            }

        } else {
            contanier.setVisibility(View.GONE);
        }
        listView.setAdapter(orderDetailAdapter);
        orderDetailAdapter.update(info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.bt_order_start:
                HttpRequestTask.orderStart(this, info.getId());
                break;

            case R.id.bt_order_pause:
                pause();
                break;

            case R.id.bt_order_finish:
                HttpRequestTask.orderFinish(this, info.getId(), info.getMileage(), "", OrderStatus.FinishOrder.getValue());
                break;
        }
    }

    private Order loadLocalOrder() {

        return LoginInfo.readProcessOrder(this.getApplicationContext());
    }

    private void pause() {
        if (OrderStatus.PauseOrder.getValue() == this.orderDetailAdapter.getMflag()) {
            HttpRequestTask.orderContinue(this, info.getId());
        } else {
            HttpRequestTask.orderPause(this, info.getId(), this.orderDetailAdapter.getMileage());
        }
    }

    public void doStartResult() {
        btStart.setEnabled(false);
        btPause.setEnabled(true);
        btFinish.setEnabled(true);
        info.setFlag(OrderStatus.RunOrder.getValue());
        startService(ServiceGPS.class, info);
    }

    public void doContinueResult() {
        btStart.setEnabled(false);
        btPause.setEnabled(true);
        btFinish.setEnabled(true);
        info.setFlag(OrderStatus.RunOrder.getValue());
        getBtPause().setText("暂停");
        getOrderDetailAdapter().upDateFlag(0, OrderStatus.RunOrder.getValue());
        startService(ServiceGPS.class, info);
    }

    public void doPauseResult() {
        getBtPause().setText("继续");
        info.setFlag(OrderStatus.PauseOrder.getValue());
        getOrderDetailAdapter().upDateFlag(0, OrderStatus.PauseOrder.getValue());
        startService(ServiceGPS.class, info);
    }

    public void doFinishResult() {
        info.setFlag(OrderStatus.FinishOrder.getValue());
        startService(ServiceGPS.class, info);
    }

    @Override
    public AbstractHandler getAbstratorHandler() {
        return abstratorHandler;
    }

}
