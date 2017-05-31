package cn.com.grentech.www.androidtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.www.androidtest.R;
import cn.com.grentech.www.androidtest.adapter.listview.ListViewAdpter;

/**
 * Created by Administrator on 2017/4/20.
 */

public class ListViewActivity extends AbstractBasicActivity {
    public ListView lv_listview_main;
    public ListViewAdpter<Info> listViewAdpter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_listview);
    }

    @Override
    protected void initView() {
        lv_listview_main=(ListView)findViewById(R.id.lv_listview_main);
    }

    @Override
    protected void bindListener() {
        lv_listview_main.setOnItemClickListener(null);
    }

    @Override
    protected void initData() {
        List<Info> list=new ArrayList<>();
        for (int i=0;i<100;i++)
        {
            Info info=new Info();
            info.line1=""+i;
            info.line2="姓名"+i;
            info.line3="年龄"+i;
            list.add(info);
        }
        listViewAdpter=new ListViewAdpter<>(this,list,R.layout.activity_listview_item);
        lv_listview_main.setAdapter(listViewAdpter);

    }

    public class Info
    {
        public boolean select=false;
        public String line1;
        public String line2;
        public String line3;
    }
}
