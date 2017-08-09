package com.powercn.grentechdriver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.adapter.MineAdapter;
import com.powercn.grentechdriver.view.TitleCommon;

/**
 * Created by Administrator on 2017/8/8.
 */

public class FindPassword3Activity extends AbstractBasicActivity {
    private TitleCommon titleCommon ;
    private Button btOk;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_findpassword3);
    }

    @Override
    protected void initView() {
        titleCommon=new TitleCommon(this,R.id.title_findpassword3);
        titleCommon.init("修改密码",this);
        btOk=(Button)findViewById(R.id.bt_find3_ok);
    }

    @Override
    protected void bindListener() {

        btOk.setOnClickListener(this);
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
            case R.id.bt_find3_ok:

                jumpForResult(BillActivity.class,1);
                break;
        }
    }

    @Override
    public AbstratorHandler getAbstratorHandler() {
        return null;
    }
}
