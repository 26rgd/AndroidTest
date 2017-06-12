package com.powercn.grentechdriver.adapter.chlid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.adapter.AbstractAdpter;

import java.util.List;

import lombok.Getter;

/**
 * Created by Administrator on 2017/5/31.
 */

public class SettingAdpter<T> extends AbstractAdpter {
    public SettingAdpter(Context context, List data, int itemres) {
        super(context, data, itemres);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView line1 = (TextView) view.findViewById(R.id.tv_title);
        TextView divisive = (TextView) view.findViewById(R.id.tv_divisive);
//        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tripwait_item_imageview);
        FrameLayout content=(FrameLayout) view.findViewById(R.id.layout_content);
        Item item=(Item)getItem(position);
        line1.setText(item.getName());

       if(item.getType()==45)
       {
           divisive.setVisibility(View.VISIBLE);
           content.setVisibility(View.GONE);

       }else
       {
           divisive.setVisibility(View.GONE);
           content.setVisibility(View.VISIBLE);

       }
        return view;
    }

    public enum Item
    {
        about("版本",0),
        hide1("hide",45),
        exit("注销",0),
        ;
        @Getter
        private String name;
        @Getter
        private int type;
        Item(String name, int type) {
            this.name = name;
            this.type = type;
        }

    }
}
