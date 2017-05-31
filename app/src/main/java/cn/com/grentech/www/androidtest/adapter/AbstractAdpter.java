package cn.com.grentech.www.androidtest.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.www.androidtest.R;


/**
 * Created by Administrator on 2017/4/20.
 */

public class AbstractAdpter<T> extends BaseAdapter implements View.OnClickListener{
    protected List<T> data;
    protected LayoutInflater inflater=null;
    protected Activity activity;
    protected  int itemres;


    public AbstractAdpter(Activity activity, List<T> data, int itemres) {
        this.activity = activity;
        if(data!=null)
        this.data = data;
        else
        this.data = new ArrayList<>();
        this.itemres = itemres;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public  View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = createItemView();
        intervalColor(vi,position);
        return vi;
    }
    public View createItemView()
    {
        View vi = inflater.inflate(itemres, null);
        return vi;
    }
    public void intervalColor(View vi,int position)
    {
        if(position%2==0)
        vi.setBackgroundColor(vi.getResources().getColor(R.color.fab_backgroundtint));
        else
            vi.setBackgroundColor(vi.getResources().getColor(R.color.alaram2_color));
    }

    @Override
    public void onClick(View v) {

    }
}
