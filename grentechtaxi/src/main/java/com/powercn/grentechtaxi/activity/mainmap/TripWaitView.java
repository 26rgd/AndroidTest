package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.unit.DateUnit;
import com.powercn.grentechtaxi.common.unit.ViewUnit;
import com.powercn.grentechtaxi.dialog.CancelOrderDialog;
import com.powercn.grentechtaxi.entity.OrderInfo;

import lombok.Getter;

/**
 * Created by Administrator on 2017/5/15.
 */

@Getter
public class TripWaitView extends MainChildView {
    private ImageView ivBack;
    private Button btnCancel;
    private Button btnShare;
   //private AddressAdpter myAdpter;
    private CancelOrderDialog userDialog;
    private AddressView addressView;
    private DriverView driverView;
    public TripWaitView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {

        ivBack=(ImageView)findViewById(R.id.iv_title_back_wait);
        btnCancel=(Button)findViewById(R.id.btn_tripwait_cancel);
        btnShare=(Button)findViewById(R.id.btn_tripwait_share);

        addressView=new AddressView(this);
        driverView=new DriverView(this);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);

        btnCancel.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    @Override
    protected void initData() {
       // myAdpter = new AddressAdpter(activity, null, R.layout.activity_trip_wait_item);
    }

    @Override
    public void setVisibility(int visibility) {
       // myAdpter.clear();
        addressView.setInfo(activity.orderInfo);
        driverView.setInfo(activity.orderInfo);
        super.setVisibility(visibility);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_title_back_wait:
                activity.jumpMianMapView(this);
                break;

            case R.id.btn_tripwait_cancel:
                  userDialog=new CancelOrderDialog(activity,R.layout.activity_cancel_order,"");
                userDialog.show();
                userDialog.setOnCliclByContact(this);
                userDialog.setOnCliclBySub(this);
                break;
            case R.id.btn_tripwait_share:
                ViewUnit.systemShare(activity,getShareMessage(activity.orderInfo));
                //ViewUnit.sendSms(activity,"",getShareMessage(activity.orderInfo));
                break;
            case  R.id.tv_order_contact:
                ViewUnit.callPhone(activity,activity.orderInfo.getDriverPhone());
                userDialog.dismiss();
                break;
            case  R.id.tv_order_sub:
                HttpRequestTask.cancelOrder(activity.orderInfo.getCaller());
                userDialog.dismiss();
                break;
        }
    }

    private String getShareMessage(OrderInfo info)
    {
        String s= DateUnit.formatDate(info.getScheduledTime().getTime(),"HH:mm")+"出发"+info.getInAddress()+"至"+info.getDownAddress()+"|"+info.getCarNo()+"|"+info.getDriverName()+"|"+info.getDriverPhone();
        return s;
    }


}
