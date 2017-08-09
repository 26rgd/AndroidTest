package com.powercn.grentechdriver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.view.TitleCommon;
import com.powercn.grentechdriver.view.UpTextView;

/**
 * Created by Administrator on 2017/8/8.
 */

public class BillActivity extends AbstractBasicActivity {
    private TitleCommon titleCommon ;
    private UpTextView upTextView1;
    private Button next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_bill);
    }

    @Override
    protected void initView() {
        titleCommon=new TitleCommon(this,R.id.title_bill);
        titleCommon.init("确认账单",this);

        upTextView1 = new UpTextView(this, R.id.uv_bill_amount);
        upTextView1.initLine1(46.57 + "", R.dimen.textSizeAnswer1, R.color.textColorMain3);
        upTextView1.initLine2("收到金额(元)", R.dimen.textSizeCallCar, R.color.textColorMain2);

        next=(Button)findViewById(R.id.bt_bill_sub);
    }

    @Override
    protected void bindListener() {
        next.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:

                finish();
                break;

            case R.id.bt_bill_sub:
                jumpFinish(MainActivity.class);
                break;
        }
    }

    @Override
    public AbstratorHandler getAbstratorHandler() {
        return null;
    }
}
