package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.common.unit.ViewUnit;
import com.powercn.grentechtaxi.entity.OrderInfo;

/**
 * Created by Administrator on 2017/6/9.
 */

public class DriverView implements View.OnClickListener {
    private AbstractChildView childView;
    private TextView tvDriverName;
    private TextView tvDriverStar;
    private TextView tvCarNo;
    private TextView tvCarColor;
    private TextView tvCarType;
    private ImageView ivCall;
    private ImageView ivSms;
    private String phone="";

    public DriverView(AbstractChildView childView) {
        this.childView = childView;
        tvDriverName = (TextView) childView.findIncludeViewById(R.id.tv_drivername);
        tvDriverStar = (TextView) childView.findIncludeViewById(R.id.tv_driverstar);
        tvCarNo = (TextView) childView.findIncludeViewById(R.id.tv_carno);
        tvCarColor = (TextView) childView.findIncludeViewById(R.id.tv_carcolor);
        tvCarType = (TextView) childView.findIncludeViewById(R.id.tv_cartype);
        ivCall = (ImageView) childView.findIncludeViewById(R.id.iv_call);
        ivSms = (ImageView) childView.findIncludeViewById(R.id.iv_sms);
        ivCall.setOnClickListener(this);
        ivSms.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_call:
                ViewUnit.callPhone(childView.getView().getContext(), phone);
                break;
            case R.id.iv_sms:
                ViewUnit.sendSms(childView.getView().getContext(), phone);
                break;
        }
    }

    public void setInfo(OrderInfo info) {
        try {
              tvDriverName.setText(info.getDriverName());
              tvDriverStar.setText(info.getDriverName());
              tvCarNo.setText(info.getCarNo());
              tvCarColor.setText("");
              tvCarType.setText(info.getCartype());
               phone=info.getDriverPhone();
        } catch (Exception e) {
        }
    }
}
