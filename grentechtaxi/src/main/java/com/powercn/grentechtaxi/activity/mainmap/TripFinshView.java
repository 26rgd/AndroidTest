package com.powercn.grentechtaxi.activity.mainmap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.AbstractAdpter;

import java.util.List;




/**
 * Created by Administrator on 2017/5/15.
 */

public class TripFinshView extends MainChildView {

    private ListView listView;
    private ImageView ivBack;
    private ImageView ivClose;
    private MyFinshAdpter myAdpter;
    public TripFinshView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        listView=(ListView)findViewById(R.id.lv_tripfinsh_list);
        ivBack=(ImageView)findViewById(R.id.iv_title_back_finsh);
        ivClose=(ImageView)findViewById(R.id.iv_tripfinsh_close);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        myAdpter=new MyFinshAdpter(activity,null,R.layout.activity_trip_finsh_item);
        MyFinshData  myData1=new MyFinshData("微信支付",R.drawable.icon_weixin);
        myData1.select=true;
        myAdpter.getData().add(myData1);
        MyFinshData myData2=new MyFinshData("支付宝支付",R.drawable.icon_alipay);
        myAdpter.getData().add(myData2);
        listView.setAdapter(myAdpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myAdpter.selectSingle(position);

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_title_back_finsh:
            case R.id.iv_tripfinsh_close:
                activity.jumpMianMapView(this);
                break;
        }
    }
    private class MyFinshAdpter extends AbstractAdpter {

        public MyFinshAdpter(Context context, List data, int itemres) {
            super(context, data, itemres);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            ImageView ivHint=(ImageView)view.findViewById(R.id.iv_tripfinsh_hint);
            ImageView ivSelect=(ImageView)view.findViewById(R.id.iv_tripfinsh_select);
            TextView tvPayName=(TextView)view.findViewById(R.id.tv_tripfinsh_payname);
            MyFinshData myData=(MyFinshData)getItem(position);
            ivHint.setImageResource(myData.ivHint);
            tvPayName.setText(myData.name);
            if(myData.select==true)
             {
                 ivSelect.setImageResource(myData.ivSelect);
             }
            else
             {
                 ivSelect.setImageResource(myData.ivNoSelect);
             }
            return view;
        }

        public void selectSingle(int postion)
        {
            for (Object object:getData())
            {
                MyFinshData line=(MyFinshData)object;
                line.select=false;
            }
            MyFinshData myData=(MyFinshData) getItem(postion);
            myData.select=true;
            notifyDataSetChanged();
        }

        @Override
        public void intervalColor(View vi, int position) {

        }
    }

    private class MyFinshData
    {
        public boolean select;
        public String name;
        public int ivHint;
        public int ivSelect=R.drawable.chk16;
        public int ivNoSelect=R.drawable.chk_un16;

        public MyFinshData(String name, int ivHint, int ivSelect, int ivNoSelect) {
            this.name = name;
            this.ivHint = ivHint;
            this.ivSelect = ivSelect;
            this.ivNoSelect = ivNoSelect;
        }

        public MyFinshData(String name, int ivHint) {
            this.name = name;
            this.ivHint = ivHint;
        }
    }
}
