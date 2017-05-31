package com.powercn.grentechtaxi.adapter.chlid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.activity.mainmap.TripWaitView;
import com.powercn.grentechtaxi.adapter.AbstractAdpter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class AddressAdpter extends AbstractAdpter {

    public AddressAdpter(Context context, List data, int itemres) {
        super(context, data, itemres);
     init();
    }
    private void init()
    {
        this.getData().clear();;
        this.getData().add("国人科技园");
        this.getData().add("深圳北站");
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView line1 = (TextView) view.findViewById(R.id.tv_tripwait_item_line1);
        TextView line2 = (TextView) view.findViewById(R.id.tv_tripwait_item_line2);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tripwait_item_imageview);

        String myData = ( String) getItem(position);
        if (position == 0) {
            line1.setText("出发地");
            imageView.setImageResource(R.drawable.icon_start);
        } else {
            line1.setText("目的地");
            imageView.setImageResource(R.drawable.icon_end);
        }
        line2.setText(myData);
        return view;
    }

    public void addStartAddress(String star,String dest)
    {
        this.getData().clear();;
        this.getData().add(star);
        this.getData().add(dest);
        notifyDataSetChanged();
    }
    @Override
    public void intervalColor(View vi, int position) {

    }


}


