package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;
import android.widget.ImageView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;

/**
 * Created by Administrator on 2017/5/15.
 */

public class TripRunView extends MainChildView {
    private ImageView ivBack;
    public TripRunView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        ivBack=(ImageView)findViewById(R.id.iv_title_back_run);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_title_back_run:
                activity.jumpMianMapView(this);
            break;
        }
    }
}
