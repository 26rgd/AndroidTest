package cn.com.grentech.www.androidtest.adapter.homepage;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.grentech.www.androidtest.R;
import cn.com.grentech.www.androidtest.adapter.AbstractRecyclerAdapter;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/4/20.
 */

public class MainAdapter<T> extends AbstractRecyclerAdapter {


    public MainAdapter(List<T> data, int itemres) {
        super(data, itemres);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder=(MyViewHolder)holder;
        ActivityInfo bean=(ActivityInfo)getItem(position);
        myViewHolder.tv_main_item_line1.setText(bean.title);
        super.onBindViewHolder(holder,position);
    }

    @Override
    public RecyclerView.ViewHolder bulid(View view) {
        return new MyViewHolder(view);
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout layout_main_item_root;
        public ImageView iv_main_item_info;
        public TextView tv_main_item_line1;
        public MyViewHolder(View itemView) {
            super(itemView);
            layout_main_item_root=(LinearLayout)itemView.findViewById(R.id.layout_main_item_root);
            iv_main_item_info=(ImageView)itemView.findViewById(R.id.iv_main_item_info);
            tv_main_item_line1=(TextView)itemView.findViewById(R.id.tv_main_item_line1);
        }
    }
}
