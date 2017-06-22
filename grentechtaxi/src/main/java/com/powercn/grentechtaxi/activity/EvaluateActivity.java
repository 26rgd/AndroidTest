package com.powercn.grentechtaxi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.activity.mainmap.AddressView;
import com.powercn.grentechtaxi.activity.mainmap.DriverView;
import com.powercn.grentechtaxi.activity.mainmap.IncludeView;
import com.powercn.grentechtaxi.adapter.AbstractRecyclerAdapter;
import com.powercn.grentechtaxi.adapter.chlid.StarRecyclerAdapter;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.entity.OrderInfo;

/**
 * Created by Administrator on 2017/6/9.
 */

public class EvaluateActivity  extends AbstractBasicActivity {
    private ImageView ivBack;
    private TextView tvContent;
    private Button btnSub;
    private RecyclerView lvStar;
    private StarRecyclerAdapter starRecyclerAdapter;
    private AddressView addressView;
    private DriverView driverView;
    private OrderInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_trip_evaluate);

    }
    @Override
    protected void initView() {
        Intent intent=this.getIntent();
        info=(OrderInfo)intent.getSerializableExtra("object");
        self=new IncludeView(this,R.id.layout_trip_evaluate);
        ivBack=(ImageView)findViewById(R.id.iv_title_back_evaluate);
        tvContent=(TextView)findViewById(R.id.edt_evaluate_content);
        btnSub=(Button)findViewById(R.id.btn_tripevaluate_sub);
        lvStar=(RecyclerView)findViewById(R.id.lv_star);
        addressView=new AddressView(self);
        driverView=new DriverView(self);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        btnSub.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        starRecyclerAdapter=new StarRecyclerAdapter(this,null,R.layout.activity_star_item);
        lvStar.setLayoutManager(starRecyclerAdapter.getLinearHorizontal());
        lvStar.setAdapter(starRecyclerAdapter);
        starRecyclerAdapter.setOnItemClickListener(new AbstractRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                starRecyclerAdapter.setStarLevel(position+1);
            }
        });
        addressView.setInfo(info);
        driverView.setInfo(info);
    }
    private void saveEvaluate()
    {
        HttpRequestTask.evaluate(info.getId(),starRecyclerAdapter.getStarLevel(),tvContent.getText().toString());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_title_back_evaluate:
                finish();
                break;
            case R.id.btn_tripevaluate_sub:
                saveEvaluate();
                jumpFinish(MainActivity.class);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}
