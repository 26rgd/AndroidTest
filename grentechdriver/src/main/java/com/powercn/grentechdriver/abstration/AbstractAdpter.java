package com.powercn.grentechdriver.abstration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/4/20.
 */

public abstract class AbstractAdpter<T> extends BaseAdapter implements View.OnClickListener {
    protected List<T> data;
    protected LayoutInflater inflater = null;
    protected Context context;
    protected int itemres;


    public AbstractAdpter(Context context, List<T> data, int itemres) {
        this.context = context;
        if (data != null)
            this.data = data;
        else
            this.data = new ArrayList<>();
        this.itemres = itemres;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

   public void clear()
   {
       this.data.clear();
       this.notifyDataSetChanged();
   }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = createItemView();
        intervalColor(vi, position);
        return vi;
    }

    public View createItemView() {
        View vi = inflater.inflate(itemres, null);
        return vi;
    }

    public void intervalColor(View vi, int position) {
//        if (position % 2 == 0)
//            vi.setBackgroundColor(ViewUnit.getColor(context, R.color.WHITE));
//        else
//            vi.setBackgroundColor(ViewUnit.getColor(context, R.color.GRAY));
    }


    public List<T> getData()
    {
        return data;
    }
    @Override
    public void onClick(View v) {

    }
}
