package com.powercn.grentechtaxi.activity.mainmap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.AbstractAdpter;
import com.powercn.grentechtaxi.adapter.chlid.AddressAdpter;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.unit.ViewUnit;
import com.powercn.grentechtaxi.dialog.CancelOrderDialog;
import com.powercn.grentechtaxi.view.UserDialog;

import java.util.List;

import lombok.Getter;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Administrator on 2017/5/15.
 */

@Getter
public class TripWaitView extends AbstractChildView {
    private ListView lv_tripwait_list;
    private ImageView ivBack;
    private ImageView ivCall;
    private ImageView ivSms;
    private Button btnCancel;
    private Button btnShare;
    private AddressAdpter myAdpter;
    private CancelOrderDialog userDialog;

    public TripWaitView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        lv_tripwait_list = (ListView) findViewById(R.id.lv_tripwait_list);
        ivBack=(ImageView)findViewById(R.id.iv_title_back_wait);

        ivCall=(ImageView)findViewById(R.id.iv_tripwait_call);
        ivSms=(ImageView)findViewById(R.id.iv_tripwait_sms);

        btnCancel=(Button)findViewById(R.id.btn_tripwait_cancel);
        btnShare=(Button)findViewById(R.id.btn_tripwait_share);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        ivSms.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        myAdpter = new AddressAdpter(activity, null, R.layout.activity_trip_wait_item);
        lv_tripwait_list.setAdapter(myAdpter);
    }

    @Override
    public void setVisibility(int visibility) {
        myAdpter.clear();
        myAdpter.getData().add(activity.orderInfo.getInAddress());
        myAdpter.getData().add(activity.orderInfo.getDownAddress());
        super.setVisibility(visibility);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_title_back_wait:
                activity.jumpMianMapView(this);
                break;

            case R.id.iv_tripwait_call:
                ViewUnit.callPhone(activity,"13510197040");

                break;

            case R.id.iv_tripwait_sms:
                ViewUnit.sendSms(activity,"13510197040");

                break;
            case R.id.btn_tripwait_cancel:
                  userDialog=new CancelOrderDialog(activity,R.layout.activity_cancel_order,"");
                userDialog.show();
                userDialog.setOnCliclByContact(this);
                userDialog.setOnCliclBySub(this);
                break;
            case R.id.btn_tripwait_share:
                ViewUnit.sendSms(activity,activity.phone);
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
//    private class MyAdpter extends AbstractAdpter {
//
//        public MyAdpter(Context context, List data, int itemres) {
//            super(context, data, itemres);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = super.getView(position, convertView, parent);
//            TextView line1 = (TextView) view.findViewById(R.id.tv_tripwait_item_line1);
//            TextView line2 = (TextView) view.findViewById(R.id.tv_tripwait_item_line2);
//            ImageView imageView = (ImageView) view.findViewById(R.id.iv_tripwait_item_imageview);
//
//            MyData myData = (MyData) getItem(position);
//            if (position == 0) {
//                line1.setText("出发地");
//                imageView.setImageResource(R.drawable.icon_start);
//            } else {
//                line1.setText("目的地");
//                imageView.setImageResource(R.drawable.icon_end);
//            }
//            line2.setText(myData.name);
//            return view;
//        }
//
//        @Override
//        public void intervalColor(View vi, int position) {
//
//        }
//    }
//
//    private class MyData {
//        public String name;
//    }
}
