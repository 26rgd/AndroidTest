package com.powercn.grentechtaxi.adapter.chlid;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.fence.PoiItem;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.AbstractAdpter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class DepartAdpter<T> extends AbstractAdpter {

    public DepartAdpter(Context context, List data, int itemres) {
        super(context, data, itemres);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= super.getView(position, convertView, parent);
        TextView tv_depart_poisearch_list_item_line1=(TextView)view.findViewById(R.id.tv_depart_poisearch_list_item_line1);
        TextView tv_depart_poisearch_list_item_line2=(TextView)view.findViewById(R.id.tv_depart_poisearch_list_item_line2);
        com.amap.api.services.core.PoiItem poiItem=(com.amap.api.services.core.PoiItem)getItem(position);
        tv_depart_poisearch_list_item_line1.setText(poiItem.toString()+poiItem.getDirection());
        tv_depart_poisearch_list_item_line2.setText(poiItem.getCityName()+poiItem.getAdName()+poiItem.getSnippet());
        return view;
    }

    @Override
    public void intervalColor(View vi, int position) {

    }
}
