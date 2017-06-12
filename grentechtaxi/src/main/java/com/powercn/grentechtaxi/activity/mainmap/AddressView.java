package com.powercn.grentechtaxi.activity.mainmap;

import android.widget.TextView;

import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.entity.OrderInfo;

/**
 * Created by Administrator on 2017/6/9.
 */

public class AddressView {
    private AbstractChildView childView;
    private TextView tvStart;
    private TextView tvEnd;

    public AddressView(AbstractChildView childView) {
        this.childView = childView;
        tvStart = (TextView) childView.findIncludeViewById(R.id.tv_add_start);
        tvEnd = (TextView) childView.findIncludeViewById(R.id.tv_add_end);
    }

    public void setInfo(OrderInfo info) {
        try {
            tvStart.setText(info.getInAddress());
            tvEnd.setText(info.getDownAddress());
        } catch (Exception e) {
        }
    }
    public void setInfo(String start,String end) {
        try {
            tvStart.setText(start);
            tvEnd.setText(end);
        } catch (Exception e) {
        }
    }
}
