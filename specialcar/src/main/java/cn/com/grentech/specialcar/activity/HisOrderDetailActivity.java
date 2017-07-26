package cn.com.grentech.specialcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
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
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.FileUnit;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.LoadLine;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.entity.Route;
import cn.com.grentech.specialcar.handler.HisOrderDetailMessageHandler;

import lombok.Getter;

import static android.view.KeyEvent.KEYCODE_HOME;

/**
 * Created by Administrator on 2017/6/30.
 */

public class HisOrderDetailActivity extends AbstractBasicActivity {
    public final static int Finish_Order = 88;
    private static AbstractHandler abstratorHandler = null;
    private ImageView ivBack;
    private TextView tvTitle;
    private ListView listView;
    @Getter
    private Button btReUp;
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
        btReUp = (Button) findViewById(R.id.bt_order_reUp);
        contanier = (LinearLayout) findViewById(R.id.contanier_button_orderdetail);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        btReUp.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        abstratorHandler = new HisOrderDetailMessageHandler(this);
        orderDetailAdapter = new OrderDetailAdapter(this, null, R.layout.list_item_orderdetail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tvTitle.setText("历史订单详情");
        info = (Order) bundle.getSerializable("order");
        contanier.setVisibility(View.GONE);
        listView.setAdapter(orderDetailAdapter);
        orderDetailAdapter.update(info);
        try {
            LoadLine loadLine = readLoadLine(info);
            if (loadLine.getListGps().size() > 0)
                btReUp.setVisibility(View.VISIBLE);
            else
                btReUp.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_order_reUp:
                LoadLine reLoadLine = readLoadLine(info);
                if (reLoadLine.getListGps().size() > 0) {
                    showToastLength("正在补传数据.....");
                    StringUnit.println(tag, "正在补传数据.....");
                    StringUnit.println(tag, "补传对象|" + GsonUnit.toJson(info));
                    this.getBtReUp().setEnabled(false);
                    HttpRequestTask.reUpGps(this, Route.bulidListJson(info, reLoadLine.getListGps()));
                } else {
                    StringUnit.println(tag, "补传数据为空");
                    showToast("补传数据为空");
                }
                break;
        }
    }

    private LoadLine readLoadLine(Order o) {
        LoadLine loadLine = (LoadLine) FileUnit.readSeriallizable(LoadLine.class.getSimpleName() + info.getId());
        if (loadLine == null) {
            loadLine = new LoadLine(o);
           // FileUnit.saveSeriallizable(LoadLine.class.getSimpleName() + info.getId(), loadLine);
        }

        return loadLine;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_HOME) {
            StringUnit.println(tag, "按了HOME键");
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    @Override
    public AbstractHandler getAbstratorHandler() {
        return abstratorHandler;
    }

}