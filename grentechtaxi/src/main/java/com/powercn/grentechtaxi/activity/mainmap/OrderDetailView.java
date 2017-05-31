package com.powercn.grentechtaxi.activity.mainmap;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.AbstractRecyclerAdapter;
import com.powercn.grentechtaxi.adapter.chlid.AddressAdpter;
import com.powercn.grentechtaxi.adapter.chlid.StarRecyclerAdapter;

import static com.powercn.grentechtaxi.R.id.btn_order_detail_sub;


/**
 * Created by Administrator on 2017/5/25.
 */

public class OrderDetailView extends AbstractChildView {
    private ListView listView;
    private RecyclerView recyclerView;
    private StarRecyclerAdapter starRecyclerAdapter;
    private AddressAdpter addressAdpter;
    private ImageView ivBack;
    private Button btnSub;
    public OrderDetailView(MainActivity activity, int resId) {
        super(activity, resId);

    }

    @Override
    protected void initView() {
        listView=(ListView)findViewById(R.id.lv_order_detail_addr);
        recyclerView=(RecyclerView)findViewById(R.id.lv_order_detail_star);
        ivBack=(ImageView)findViewById(R.id.iv_order_detail_back);
        btnSub=(Button)findViewById(R.id.btn_order_detail_sub);

    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        btnSub.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        starRecyclerAdapter=new StarRecyclerAdapter(activity,null,R.layout.activity_star_item);
        addressAdpter=new AddressAdpter(activity,null,R.layout.activity_trip_wait_item);
        recyclerView.setAdapter(starRecyclerAdapter);
        recyclerView.setLayoutManager(starRecyclerAdapter.getLinearHorizontal());
        listView.setAdapter(addressAdpter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_order_detail_back:
                this.setVisibility(View.GONE);
                activity.tripOrderView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_order_detail_sub:
                activity.tripEvaluateView.setVisibility(View.VISIBLE);
                break;

        }
    }
    @Override
    public void setVisibility(int visibility) {
        starRecyclerAdapter.setStarLevel(activity.orderInfo.getEvaluateAnswer());
        if(activity.orderInfo.getEvaluateAnswer()==null||activity.orderInfo.getEvaluateAnswer()==0)
        {
            starRecyclerAdapter.setStarLevel(0);
            starRecyclerAdapter.setStarLevel(activity.orderInfo.getEvaluateAnswer());
            recyclerView.setVisibility(View.GONE);
            btnSub.setVisibility(View.VISIBLE);
        }
      else
        {
            btnSub.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            starRecyclerAdapter.setOnItemClickListener(null);

        }
        addressAdpter.addStartAddress(activity.orderInfo.getInAddress(),activity.orderInfo.getDownAddress());
        super.setVisibility(visibility);
    }
}
