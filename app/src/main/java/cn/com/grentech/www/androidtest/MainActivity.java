package cn.com.grentech.www.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import cn.com.grentech.www.androidtest.activity.AbstractBasicActivity;
import cn.com.grentech.www.androidtest.activity.ListViewActivity;
import cn.com.grentech.www.androidtest.activity.WebSocketActivity;
import cn.com.grentech.www.androidtest.adapter.AbstractRecyclerAdapter;
import cn.com.grentech.www.androidtest.adapter.homepage.ActivityInfo;
import cn.com.grentech.www.androidtest.adapter.homepage.MainAdapter;
import cn.com.grentech.www.androidtest.common.unit.StringUnit;
import cn.com.grentech.www.androidtest.service.GrentechService;

public class MainActivity extends AbstractBasicActivity implements View.OnClickListener {
    public RecyclerView rv_main_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        StringUnit.println("MainActivity|"+ Process.myPid());
        Intent intent=new Intent(MainActivity.this, GrentechService.class);
        startService(intent);
    }

    @Override
    protected void initView() {
        rv_main_link = (RecyclerView) findViewById(R.id.rv_main_link);
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void initData() {
        rv_main_link.setHasFixedSize(true);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        rv_main_link.setLayoutManager(layoutManager);
        List<ActivityInfo> list = new ArrayList<>();
//        for (int i = 0; i <199; i++) {
//            ActivityInfo activityInfo = new ActivityInfo();
//            activityInfo.title = i + "";
//            list.add(activityInfo);
//        }

        ActivityInfo activityInfo=new ActivityInfo();
        activityInfo.title="listView";
        activityInfo.cls=ListViewActivity.class;
        list.add(activityInfo);

        ActivityInfo activityInfo1=new ActivityInfo();
        activityInfo1.title="websocket";
        activityInfo1.cls=WebSocketActivity.class;
        list.add(activityInfo1);

        final MainAdapter<ActivityInfo> mainAdapter = new MainAdapter(list, R.layout.activity_main_item);
        rv_main_link.setAdapter(mainAdapter);
        DividerItemDecoration bold=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);


        DividerItemDecoration bold1=new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL);

        rv_main_link.addItemDecoration(bold);
        rv_main_link.addItemDecoration(bold1);
        mainAdapter.setOnItemClickListener(new AbstractRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                mainAdapter.notifyDataSetChanged();
                ActivityInfo activityInfo1=(ActivityInfo) mainAdapter.getItem(position);
                jumpNotFinish(activityInfo1.cls);

            }
        });
    }



    @Override
    public void onClick(View v) {

    }
}
