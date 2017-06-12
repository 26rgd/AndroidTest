package com.powercn.grentechtaxi.adapter.chlid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.AbstractRecyclerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class StarRecyclerAdapter extends AbstractRecyclerAdapter {
    private final int  size=5;
    private int starLevel=0;
    public StarRecyclerAdapter(Context context,List data, int itemres) {

        super(context,data, itemres);
        init();
    }

    private void init()
    {
        for(int i=0;i<size;i++)
        {
            getData().add(i);
        }
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StarRecyclerAdapter.StartViewHolder myViewHolder=(StarRecyclerAdapter.StartViewHolder)holder;
        if(position<starLevel)
            myViewHolder.stat.setImageResource(R.drawable.icon_score_blue);
        else
            myViewHolder.stat.setImageResource(R.drawable.icon_score);
        super.onBindViewHolder(holder,position);
    }

    @Override
    public RecyclerView.ViewHolder bulid(View view) {
        return new StarRecyclerAdapter.StartViewHolder(view);
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel==null?0:starLevel;
        notifyDataSetChanged();
    }

    public int getStarLevel() {
        return starLevel;
    }

    private class StartViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView stat;

        public StartViewHolder(View itemView) {
            super(itemView);
            stat=(ImageView)itemView.findViewById(R.id.iv_start_item);
        }
    }
}
