package com.powercn.grentechtaxi.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.AbstractAdpter;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.unit.DateUnit;
import com.powercn.grentechtaxi.common.unit.ViewUnit;
import com.powercn.grentechtaxi.entity.CallOrderStatusEnum;
import com.powercn.grentechtaxi.entity.OrderInfo;
import com.powercn.grentechtaxi.handle.OrderListMessageHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 */

public class OrderListActivity  extends AbstractBasicActivity  {
    private ListView orderList;
    private ImageView ivBack;
    private MyOrderAdpter myOrderAdpter;
    public static OrderListMessageHandler orderListMessageHandler = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_trip_order);
        HttpRequestTask.getAllOrderByMobile(MainActivity.loginInfo.phone, 1, 200);
    }
    @Override
    protected void initView() {
        orderList = (ListView) findViewById(R.id.lv_triporder_order);
        ivBack = (ImageView) findViewById(R.id.iv_title_back_order);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
     orderListMessageHandler=new OrderListMessageHandler(this);
        List<OrderInfo> list = new ArrayList<>();

        myOrderAdpter = new MyOrderAdpter(this, list, R.layout.activity_trip_order_item_new);
        orderList.setAdapter(myOrderAdpter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back_order:
                finish();
                break;
        }
    }

    private class MyOrderAdpter extends AbstractAdpter {

        public MyOrderAdpter(Context context, List data, int itemres) {
            super(context, data, itemres);
            //  HttpRequestTask.getAllOrderByMobile(activity.loginInfo.phone, 1, 200);
        }

        @Override
        public void intervalColor(View vi, int position) {

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            OrderInfo info = (OrderInfo) getItem(position);

            TextView status=(TextView)view.findViewById(R.id.tv_order_status);
            TextView cartype=(TextView)view.findViewById(R.id.tv_order_cartype);
            TextView time=(TextView)view.findViewById(R.id.tv_order_time);
            TextView tvDetail=(TextView)view.findViewById(R.id.tv_Order_detail);
            TextView line1=(TextView)view.findViewById(R.id.tv_order_line1);
            TextView line2=(TextView)view.findViewById(R.id.tv_order_line2);
            ImageView detail=(ImageView)view.findViewById(R.id.iv_order_detail);
            cartype.setText("网约车");
            status.setText(info.getStatus().getName());
            time.setText(DateUnit.formatDate(info.getCreateTime().getTime(), "yyyy-MM-dd HH:mm:ss"));
            line1.setText(info.getInAddress());
            line2.setText(info.getDownAddress());
            detail.setTag(info);
            tvDetail.setTag(info);
            if(info.getStatus()== CallOrderStatusEnum.RUNNING||info.getStatus()==CallOrderStatusEnum.BOOKED)
            {
                status.setTextColor(ViewUnit.getColor(context,R.color.textColorOrder3));
                cartype.setTextColor(ViewUnit.getColor(context,R.color.textColorOrder3));
            }else
            {
                status.setTextColor(ViewUnit.getColor(context,R.color.textColorOrder1));
                cartype.setTextColor(ViewUnit.getColor(context,R.color.textColorOrder1));
            }
            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jump(v);
                }
            });
            tvDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jump(v);
                }
            });
            return view;
        }

        public void update(List<OrderInfo> list) {
            this.getData().addAll(list);
            notifyDataSetChanged();
        }
    }

    private void jump(View v)
    {
        OrderInfo info=(OrderInfo) v.getTag();
        jumpForResult(OrderInfoActivity.class,info,66);
//        activity.orderInfo=(OrderInfo) v.getTag();
//        if(activity.orderInfo.getStatus()== CallOrderStatusEnum.RUNNING)
//        {
//            activity.jumpMianMapView(activity.tripOrderView);
//            activity.hideMainView();
//            activity.tripWaitView.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            activity.jumpMianMapView(activity.tripOrderView);
//            activity.hideMainView();
//            activity.orderDetailView.setVisibility(View.VISIBLE);
//        }

    }

    public static void sendHandleMessage(String key, String content, Object object) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(key, content);
            Message msg = new Message();
            msg.what = 0;
            msg.setData(bundle);
            msg.obj = object;
            orderListMessageHandler.sendMessage(msg);
        } catch (Exception e) {
        }
    }

    public void updateData(List list) {
        myOrderAdpter.update(list);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}
