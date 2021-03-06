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

public class OrderDetailactivity extends AbstractBasicActivity {
    private TitleCommon title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_orderdetail);
    }

    @Override
    protected void initView() {
        title=new TitleCommon(this,R.id.title_orderdetail);
        title.init("订单详情",this);

    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void initData() {



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
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

