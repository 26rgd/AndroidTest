package cn.com.grentech.specialcar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractAdapter;
import cn.com.grentech.specialcar.common.unit.DateUnit;
import cn.com.grentech.specialcar.entity.Order;

/**
 * Created by Administrator on 2017/6/15.
 */

public class OrderListAdapter  extends AbstractAdapter {
    public OrderListAdapter(Context context, List data, int itemres) {
        super(context, data, itemres);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= super.getView(position, convertView, parent);
        Order object=(Order)getItem(position);
        TextView tvLine1=(TextView)view.findViewById(R.id.tv_line1);
        TextView tvLine2=(TextView)view.findViewById(R.id.tv_line2);
        tvLine1.setText(object.getFrom()+" -- "+object.getTo());
        tvLine2.setText(DateUnit.formatDate(object.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
        return view;
    }

    public void update(List<Order> list)
    {
        getData().clear();
        getData().addAll(list);
        notifyDataSetChanged();
    }
}
