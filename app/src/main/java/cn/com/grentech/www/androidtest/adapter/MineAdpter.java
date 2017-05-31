package cn.com.grentech.www.androidtest.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import java.util.List;

import cn.com.grentech.www.androidtest.R;
import cn.com.grentech.www.androidtest.activity.ListViewActivity;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Administrator on 2017/5/8.
 */

public class MineAdpter extends AbstractAdpter {
    private int separtorres ;
    public MineAdpter(Activity activity, List data, int itemres) {
        super(activity, data, itemres);
    }
    public MineAdpter(Activity activity, List data, int itemres,int separtorres) {
        super(activity, data, itemres);
        this.separtorres=separtorres;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewActivity.Info info = (ListViewActivity.Info) getItem(position);
        View view =createItemView(position);
        if(this.getItem(position)!=null)
        {

        }
        return view;
    }

    public View createItemView(int position)
    {
        if(this.getItem(position)==null)
        {
            View vi = inflater.inflate(separtorres, null);
            return vi;
        }
        View vi = inflater.inflate(itemres, null);
        return vi;
    }

    public class MineInfo
    {
        public int iconres;
        public int tile;
    }
}
