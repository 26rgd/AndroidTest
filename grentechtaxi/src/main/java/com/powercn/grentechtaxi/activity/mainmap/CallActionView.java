package com.powercn.grentechtaxi.activity.mainmap;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.AbstractAdpter;
import com.powercn.grentechtaxi.adapter.chlid.AddressAdpter;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.unit.DateUnit;
import com.powercn.grentechtaxi.common.unit.ViewUnit;
import com.powercn.grentechtaxi.dialog.RequestProgressDialog;
import com.powercn.grentechtaxi.entity.CallOrder;

import java.util.List;

import lombok.Getter;


import static com.powercn.grentechtaxi.R.id.lv_tripwait_list;
import static com.powercn.grentechtaxi.R.id.tv_callcar_call_sub;


/**
 * Created by Administrator on 2017/5/12.
 */
@Getter
public class CallActionView extends AbstractChildView {
    private TextView tv_call_time;
    private TextView tv_callcar_call_sub;
    private TextView tvMileage;
    private TextView tvMoney;
    private TextView tv_callcar_call_cance;
    private ImageView iv_time;
    private ListView listView;
    private AddressAdpter myAdpter;
    private ImageView ivBack;

    public CallActionView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        tv_call_time = (TextView) findViewById(R.id.tv_call_time);
        tv_callcar_call_sub = (TextView) findViewById(R.id.tv_callcar_call_sub);
        tv_callcar_call_cance = (TextView) findViewById(R.id.tv_callcar_call_cancel);
        tvMileage = (TextView) findViewById(R.id.tv_mileage);
        tvMoney = (TextView) findViewById(R.id.tv_money);

        iv_time = (ImageView) findViewById(R.id.iv_time);
        ivBack = (ImageView) findViewById(R.id.iv_callaction_back);

        listView = (ListView) findViewById(R.id.lv_callaction);
    }

    @Override
    protected void bindListener() {
        tv_call_time.setOnClickListener(this);
        tv_callcar_call_sub.setOnClickListener(this);
        tv_callcar_call_cance.setOnClickListener(this);
        // iv_time.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        myAdpter = new AddressAdpter(activity, null, R.layout.activity_trip_wait_item);
        listView.setAdapter(myAdpter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_callcar_call_cancel:
                activity.jumpMianMapView(this);

//               activity.hideMainView();
//               activity.tripFinshView.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_callcar_call_sub:
                RequestProgressDialog requestProgressDialog = new RequestProgressDialog(activity);
                requestProgressDialog.show("正在叫车......");
                setVisibility(View.GONE);
                getCallOrder();

                activity.callCarView.setVisibility(View.VISIBLE);

                break;

            case R.id.tv_call_time:
                //showPopupWindow(activity.mainMapView.getLayout_mainmap_popupwindow_postion());
                // activity.deteTimeView.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_time:
                // activity.deteTimeView.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_selecttitme_sub:
                break;
            case R.id.iv_callaction_back:
                activity.jumpMianMapView(this);
                break;
        }
    }

    private void getCallOrder() {
        try {
            CallOrder callOrder = new CallOrder();
            callOrder.setPhone(activity.phone);
            callOrder.setFrom(activity.callCarView.getTvStart().getText().toString());
            callOrder.setTo(activity.callCarView.getTvDest().getText().toString());
            if (activity.startAddr.dlat != 0 && activity.startAddr.dlng != 0) {
                callOrder.setFromLat(activity.startAddr.dlat);
                callOrder.setFromLng(activity.startAddr.dlng);
            } else {
                callOrder.setFromLat(activity.mainMapView.getMyLocation().getLatitude());
                callOrder.setFromLng(activity.mainMapView.getMyLocation().getLongitude());
            }
            callOrder.setToLat(activity.destAddr.dlat);
            callOrder.setToLng(activity.destAddr.dlng);
            if (tv_call_time.getText().toString().contains("现在")) {
                callOrder.setOrdertype(0);//订单类型 0 即时叫车 1预约叫车 2车辆指派
                callOrder.setScheduledtime(DateUnit.formatDate(System.currentTimeMillis() + 90 * 1000, "yyyy-MM-dd HH:mm"));
            } else {
                callOrder.setOrdertype(1);//订单类型 0 即时叫车 1预约叫车 2车辆指派
                callOrder.setScheduledtime(tv_call_time.getText().toString());
            }

            HttpRequestTask.callOrder(callOrder);

        } catch (Exception e) {
        }
    }


    @Override
    public void setVisibility(int visibility) {
        myAdpter.clear();
        tv_call_time.setText(activity.callCarView.getTvShowTime().getText());
        String s="约"+String.valueOf(activity.totalMileage)+"公里";
        Spannable sp=new SpannableString(s);
        int len=s.length()-3;
        sp.setSpan(new AbsoluteSizeSpan(28,true),1,len+1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvMileage.setText(sp);

        s="约"+String.valueOf(activity.totalMoney)+"元";
        Spannable sp1=new SpannableString(s);
        len=s.length()-2;
        sp1.setSpan(new AbsoluteSizeSpan(28,true),1,len+1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvMoney.setText(sp1);
       // tvMoney.setText(String.valueOf(activity.totalMoney));
        myAdpter.getData().add(activity.startAddr.name);
        myAdpter.getData().add(activity.destAddr.name);
        super.setVisibility(visibility);

    }


}
