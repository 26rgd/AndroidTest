package com.powercn.grentechtaxi.activity.mainmap;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.activity.EvaluateActivity;
import com.powercn.grentechtaxi.adapter.AbstractAdpter;
import com.powercn.grentechtaxi.entity.OrderInfo;

import java.util.List;




/**
 * Created by Administrator on 2017/5/15.
 */

public class TripFinshView extends MainChildView {

    private ImageView ivBack;
    private ImageView ivClose;
    private TextView tvDriver;
    private TextView tvAccount;
    private Button btSub;
    private AddressView addressView;
    public TripFinshView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        ivBack=(ImageView)findViewById(R.id.iv_title_back_finsh);
        ivClose=(ImageView)findViewById(R.id.iv_tripfinsh_close);

        tvDriver=(TextView)findViewById(R.id.tv_driver_pay);
        tvAccount=(TextView)findViewById(R.id.tv_driver_pay_account);
        btSub=(Button)findViewById(R.id.bt_evaluate_finish);
        addressView=new AddressView(this);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        btSub.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_title_back_finsh:
            case R.id.iv_tripfinsh_close:
                activity.jumpMianMapView(this);
                break;

            case R.id.bt_evaluate_finish:
                activity.jumpMianMapView(this);
               activity.jumpForResult(EvaluateActivity.class,activity.webOrderInfo,77);
                break;
        }
    }


    @Override
    public void setVisibility(int visibility) {
        if(visibility==View.VISIBLE)
        {
            activity.tripWaitView.setVisibility(View.GONE);
            activity.mainMapView.setVisibility(View.GONE);
            String msg="请现金支付"+activity.webOrderInfo.getDriverName()+"车费";
            tvDriver.setText(msg);
            Float d=activity.webOrderInfo.getAmount()==null?0:activity.webOrderInfo.getAmount();
            String account=String.valueOf(d);
            tvAccount.setText(account);
            addressView.setInfo(activity.webOrderInfo);
        }
        super.setVisibility(visibility);
    }
}
