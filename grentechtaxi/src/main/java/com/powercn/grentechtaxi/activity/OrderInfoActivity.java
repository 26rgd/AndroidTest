package com.powercn.grentechtaxi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.activity.mainmap.AbstractChildView;
import com.powercn.grentechtaxi.activity.mainmap.AddressView;
import com.powercn.grentechtaxi.activity.mainmap.DriverView;
import com.powercn.grentechtaxi.activity.mainmap.IncludeView;
import com.powercn.grentechtaxi.adapter.chlid.StarRecyclerAdapter;
import com.powercn.grentechtaxi.entity.OrderInfo;

/**
 * Created by Administrator on 2017/6/9.
 */

public class OrderInfoActivity extends AbstractBasicActivity {
    private RecyclerView recyclerView;
    private StarRecyclerAdapter starRecyclerAdapter;
    private ImageView ivBack;
    private Button btnSub;
    private AddressView addressView;
    private DriverView driverView;
    private OrderInfo info;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_order_detail);

    }
    @Override
    protected void initView() {
        Intent intent=this.getIntent();
        info=(OrderInfo)intent.getSerializableExtra("object");
        self=new IncludeView(this,R.id.layout_oder_detail);
        recyclerView=(RecyclerView)findViewById(R.id.lv_order_detail_star);
        ivBack=(ImageView)findViewById(R.id.iv_order_detail_back);
        btnSub=(Button)findViewById(R.id.btn_order_detail_sub);
        addressView=new AddressView(self);
        driverView=new DriverView(self);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        btnSub.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        starRecyclerAdapter=new StarRecyclerAdapter(this,null,R.layout.activity_star_item);
        recyclerView.setAdapter(starRecyclerAdapter);
        recyclerView.setLayoutManager(starRecyclerAdapter.getLinearHorizontal());
        addressView.setInfo(info);
        driverView.setInfo(info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_order_detail_back:
                finish();
                break;
            case R.id.btn_order_detail_sub:
                jumpForResult(EvaluateActivity.class,info,665);
                break;

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}
