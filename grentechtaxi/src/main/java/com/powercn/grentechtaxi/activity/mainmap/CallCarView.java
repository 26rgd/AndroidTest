package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.common.unit.ViewUnit;

import lombok.Getter;
import lombok.Setter;

import static com.powercn.grentechtaxi.R.id.et_mainmap_taxi_depart;
import static com.powercn.grentechtaxi.R.id.et_mainmap_taxi_dest;

/**
 * Created by Administrator on 2017/5/9.
 */
@Getter
public class CallCarView extends AbstractChildView {


    private TextView tvShowTime;
    private LinearLayout selectTime;
//    private TextView et_mainmap_taxi_depart;
//    private TextView et_mainmap_taxi_dest;
    private TextView tvStart;
    private TextView tvDest;

//    @Setter
//    private LatLonPoint start;
//    @Setter
//    private LatLonPoint dest;

    public CallCarView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {


        tvShowTime= (TextView) findViewById(R.id.tv_select_time);

                selectTime=(LinearLayout)findViewById(R.id.layout_time);
        tvStart = (TextView) findViewById(et_mainmap_taxi_depart);
        tvDest = (TextView) findViewById(et_mainmap_taxi_dest);

    }

    @Override
    protected void bindListener() {

       // tv_callcar_call.setOnClickListener(this);
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
//
//            case R.id.tv_callcar_call:
//                if(et_mainmap_taxi_dest.getText().toString().trim().length()==0)
//                {
//                    activity.showToast("请选择目的地.");
//                    return;
//                }
//                 setVisibility(View.GONE);
//                activity.callActionView.setVisibility(View.VISIBLE);
//             break;
            case R.id.layout_time:
            activity.deteTimeView.setVisibility(View.VISIBLE);
                    break;
            case et_mainmap_taxi_dest:
                activity.destinationView.getEt_destination_search_edit().setText("");
                activity.destinationView.getEt_destination_search_edit().clearFocus();
                activity.destinationView.getView().setVisibility(View.VISIBLE);
                break;
            case et_mainmap_taxi_depart:
                LatLonPoint latLonPoint=new LatLonPoint(activity.mainMapView.myLocation.getLatitude(),activity.mainMapView.myLocation.getLongitude());
                activity.departView.getEt_depart_search_edit().setText("");
                activity.departView.getEt_depart_search_edit().clearFocus();
                activity.departView.onSearchNear(latLonPoint);
                activity.departView.getView().setVisibility(View.VISIBLE);
                break;
        }
    }





}
