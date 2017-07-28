package com.powercn.grentechdriver.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractAdpter;

import java.util.List;



/**
 * Created by Administrator on 2017/5/9.
 */

public class MineAdapter extends AbstractAdpter {
    public MineAdapter(Context context, List data, int itemres) {

        super(context, data, itemres);
       // this.data.add(PopuWindowInfo.UserInfo);
        this.data.add(PopuWindowInfo.Order);
        this.data.add(PopuWindowInfo.PayOff);
        this.data.add(PopuWindowInfo.Service);
        this.data.add(PopuWindowInfo.Setting);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= super.getView(position, convertView, parent);
        ImageView lvIcon=(ImageView)view.findViewById(R.id.lv_mainside_icon);
        TextView tvMenu=(TextView)view.findViewById(R.id.tv_mainside_menu);
        PopuWindowInfo popuWindowInfo=(PopuWindowInfo)data.get(position);
        lvIcon.setImageResource(popuWindowInfo.getIconRes());
        tvMenu.setText(popuWindowInfo.getName());
        return view;
    }
    @Override
    public void intervalColor(View vi, int position) {

    }
    public enum PopuWindowInfo {
        UserInfo("资料", R.drawable.icon_head_mine, 0),
        Order("订单",R.drawable.icon_trip_mine, 1),
        Service("客服中心",R.drawable.icon_service_mine,2),
        Setting("设置",R.drawable.icon_set_mine,3),
        PayOff("我的车辆",R.drawable.icon_payment_mine,4);
        String name;
        int iconRes;
        int index;
        private PopuWindowInfo(String name,int iconRes, int index) {
            this.name = name;
            this.iconRes=iconRes;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }

        public int getIconRes()
        {
            return  iconRes;
        }
    }

}
