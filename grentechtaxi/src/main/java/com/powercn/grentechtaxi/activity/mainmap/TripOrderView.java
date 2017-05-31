package com.powercn.grentechtaxi.activity.mainmap;

import android.content.Context;
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
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.entity.CallOrderStatusEnum;
import com.powercn.grentechtaxi.entity.OrderInfo;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by Administrator on 2017/5/16.
 */

public class TripOrderView extends AbstractChildView {
    private ListView orderList;
    private ImageView ivBack;

    public TripOrderView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    private MyOrderAdpter myOrderAdpter;

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
        List<OrderInfo> list = new ArrayList<>();

        myOrderAdpter = new MyOrderAdpter(activity, list, R.layout.activity_trip_order_item_new);
        orderList.setAdapter(myOrderAdpter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back_order:
                activity.jumpMianMapView(this);
                break;
        }
    }

    private class MyOrderAdpter extends AbstractAdpter {

        public MyOrderAdpter(Context context, List data, int itemres) {
            super(context, data, itemres);
        }

        @Override
        public void intervalColor(View vi, int position) {

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            OrderInfo orderInfo = (OrderInfo) getItem(position);

            TextView status=(TextView)view.findViewById(R.id.tv_order_status);
            TextView cartype=(TextView)view.findViewById(R.id.tv_order_cartype);
            TextView time=(TextView)view.findViewById(R.id.tv_order_time);
            TextView tvDetail=(TextView)view.findViewById(R.id.tv_Order_detail);
            TextView line1=(TextView)view.findViewById(R.id.tv_order_line1);
            TextView line2=(TextView)view.findViewById(R.id.tv_order_line2);
            ImageView detail=(ImageView)view.findViewById(R.id.iv_order_detail);
            cartype.setText("网约车");
            status.setText(orderInfo.getStatus().getName());
            time.setText(DateUnit.formatDate(orderInfo.getCreateTime().getTime(), "yyyy-MM-dd HH:mm:ss"));
            line1.setText(orderInfo.getInAddress());
            line2.setText(orderInfo.getDownAddress());
            detail.setTag(orderInfo);
            tvDetail.setTag(orderInfo);
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
        activity.orderInfo=(OrderInfo) v.getTag();
        if(activity.orderInfo.getStatus()== CallOrderStatusEnum.RUNNING)
        {
            activity.jumpMianMapView(activity.tripOrderView);
            activity.hideMainView();
            activity.tripWaitView.setVisibility(View.VISIBLE);
        }
            else
        {
            activity.jumpMianMapView(activity.tripOrderView);
            activity.hideMainView();
            activity.orderDetailView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void setVisibility(int visibility) {
        HttpRequestTask.getAllOrderByMobile(activity.phone, 1, 200);
        super.setVisibility(visibility);
    }

    public void updateData(List list) {
        myOrderAdpter.update(list);
    }
}
