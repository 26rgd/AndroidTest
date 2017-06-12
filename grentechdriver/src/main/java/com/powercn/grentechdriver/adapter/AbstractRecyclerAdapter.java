package com.powercn.grentechdriver.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by Administrator on 2017/4/20.
 */
@Getter
public abstract class AbstractRecyclerAdapter<T> extends RecyclerView.Adapter {
    private List<T> data;
    private int itemres;
    private Context context;
    private OnItemClickListener onItemClickListener = null;

    public T getItem(int postion) {
        return data.get(postion);
    }

    public AbstractRecyclerAdapter(Context context ,List<T> data, int itemres) {
        this.context=context;
        if(data!=null)
        this.data = data;
        else
        this.data=new ArrayList<>();
        this.itemres = itemres;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemres, null);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(layoutParams);
        return bulid(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClickListener(v, position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public LinearLayoutManager getLinearVertical()
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return  linearLayoutManager;
    }

    public LinearLayoutManager getLinearHorizontal()
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return  linearLayoutManager;
    }

    public abstract RecyclerView.ViewHolder bulid(View view);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClickListener(View view, int position);
    }

    public class ItemStatus
    {
        public boolean isSelect;
    }


}
