package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.activity.DateSelectorActivity;

import lombok.Getter;

import static com.powercn.grentechtaxi.R.id.et_mainmap_taxi_depart;
import static com.powercn.grentechtaxi.R.id.et_mainmap_taxi_dest;

/**
 * Created by Administrator on 2017/5/9.
 */
@Getter
public class CallCarView extends MainChildView {


    private TextView tvShowTime;
    private LinearLayout selectTime;

    private TextView tvStart;
    private TextView tvDest;


    public CallCarView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        tvShowTime = (TextView) findViewById(R.id.tv_select_time);
        selectTime = (LinearLayout) findViewById(R.id.layout_time);
        tvStart = (TextView) findViewById(et_mainmap_taxi_depart);
        tvDest = (TextView) findViewById(et_mainmap_taxi_dest);
    }

    @Override
    protected void bindListener() {
        selectTime.setOnClickListener(this);
        tvDest.setOnClickListener(this);
        tvStart.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_time:
                //activity.deteTimeView.setVisibility(View.VISIBLE);
                activity.jumpForResult(DateSelectorActivity.class,MainActivity.selectTime,"selecttime",this.getTvShowTime().getText().toString());
                break;
            case et_mainmap_taxi_dest:
                activity.destinationView.getEt_destination_search_edit().setText("");
                activity.destinationView.getEt_destination_search_edit().clearFocus();
                activity.destinationView.seachHotPoi();
                activity.destinationView.getView().setVisibility(View.VISIBLE);
                break;
            case et_mainmap_taxi_depart:
                if(activity.mainMapView.myLocation!=null)
                {
                    LatLonPoint latLonPoint = new LatLonPoint(activity.mainMapView.myLocation.getLatitude(), activity.mainMapView.myLocation.getLongitude());
                    activity.departView.getEt_depart_search_edit().setText("");
                    activity.departView.getEt_depart_search_edit().clearFocus();
                    activity.departView.onSearchNear(latLonPoint);
                    activity.departView.getView().setVisibility(View.VISIBLE);
                }
                else
                {
                    activity.departView.getView().setVisibility(View.VISIBLE);
                }
                break;
        }
    }

}
