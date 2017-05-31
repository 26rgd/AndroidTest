package com.powercn.grentechtaxi.activity.mainmap;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.AbstractRecyclerAdapter;
import com.powercn.grentechtaxi.adapter.chlid.StarRecyclerAdapter;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;


import java.util.ArrayList;
import java.util.List;




/**
 * Created by Administrator on 2017/5/19.
 */

public class TripEvaluateView extends AbstractChildView {
    private ImageView ivBack;
    private TextView tvContent;
    private Button btnSub;
    private RecyclerView lvStar;
    private StarRecyclerAdapter starRecyclerAdapter;
    public TripEvaluateView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        ivBack=(ImageView)findViewById(R.id.iv_title_back_evaluate);
        tvContent=(TextView)findViewById(R.id.edt_evaluate_content);
        btnSub=(Button)findViewById(R.id.btn_tripevaluate_sub);
        lvStar=(RecyclerView)findViewById(R.id.lv_star);

    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        btnSub.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        starRecyclerAdapter=new StarRecyclerAdapter(activity,null,R.layout.activity_star_item);
        lvStar.setLayoutManager(starRecyclerAdapter.getLinearHorizontal());
        lvStar.setAdapter(starRecyclerAdapter);
        starRecyclerAdapter.setOnItemClickListener(new AbstractRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                starRecyclerAdapter.setStarLevel(position+1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_title_back_evaluate:
                activity.jumpMianMapView(this);
                break;
            case R.id.btn_tripevaluate_sub:
                saveEvaluate();
                activity.jumpMianMapView(this);
                break;
        }
    }

    private void saveEvaluate()
    {
        HttpRequestTask.evaluate(25,starRecyclerAdapter.getStarLevel(),tvContent.getText().toString());
    }




}
