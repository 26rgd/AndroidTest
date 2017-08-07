package com.powercn.grentechdriver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.adapter.OrderListAdapter;
import com.powercn.grentechdriver.entity.OrderInfo;
import com.powercn.grentechdriver.view.TitleCommon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class OrderListActivity extends AbstractBasicActivity {
    private TitleCommon title;
    private ListView listView;
    private OrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_orderlist);
    }

    @Override
    protected void initView() {
        title = new TitleCommon(this, R.id.title_orderlist);
        title.init("我的订单", this);
        listView = (ListView) findViewById(R.id.orderlist_listview);
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void initData() {
        List<OrderInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderInfo orderInfo = new OrderInfo(i + 12, new Date(), "国人", "双龙", null);
            list.add(orderInfo);
        }

        orderListAdapter = new OrderListAdapter(this, list, R.layout.item_orderlist);
        listView.setAdapter(orderListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
        }
    }

    @Override
    public AbstratorHandler getAbstratorHandler() {
        return null;
    }
}