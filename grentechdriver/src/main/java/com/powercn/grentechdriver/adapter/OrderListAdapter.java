package com.powercn.grentechdriver.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractAdpter;
import com.powercn.grentechdriver.common.unit.DateUnit;
import com.powercn.grentechdriver.common.unit.ViewUnit;
import com.powercn.grentechdriver.entity.CallOrderStatusEnum;
import com.powercn.grentechdriver.entity.OrderInfo;
import com.powercn.grentechdriver.view.AddrItem1;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class OrderListAdapter extends AbstractAdpter {

    public OrderListAdapter(Context context, List data, int itemres) {
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
        Activity activity=(Activity)context;

        TextView status=(TextView)view.findViewById(R.id.orderlist_status);
        TextView time=(TextView)view.findViewById(R.id.orderlist_time);
        AddrItem1 start=new AddrItem1(view.findViewById(R.id.orderlist_addr1));
        AddrItem1 end=new AddrItem1(view.findViewById(R.id.orderlist_addr2));

        ImageView detail=(ImageView)view.findViewById(R.id.orderlist_detail);
        start.init(info.getInAddress(),R.drawable.icon_qidian);
        end.init(info.getDownAddress(),R.drawable.icon_zhongdian);
       // status.setText(info.getStatus().getName());
        time.setText(DateUnit.formatDate(info.getCreateTime().getTime(), "yyyy-MM-dd HH:mm:ss"));

        detail.setTag(info);


        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    public void update(List<OrderInfo> list) {
        this.getData().addAll(list);
        notifyDataSetChanged();
    }
}
