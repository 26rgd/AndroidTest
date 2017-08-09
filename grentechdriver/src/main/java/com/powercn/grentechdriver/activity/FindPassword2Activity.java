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

public class FindPassword2Activity extends AbstractBasicActivity {
    private TitleCommon titleCommon ;
    private Button next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_findpassword2);
    }

    @Override
    protected void initView() {
        titleCommon=new TitleCommon(this,R.id.title_findpassword2);
        titleCommon.init("修改密码",this);

        next=(Button)findViewById(R.id.bt_find2_sub);
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

            case R.id.bt_find2_sub:

                jumpForResult(FindPassword3Activity.class,1);
                break;
        }
    }

    @Override
    public AbstratorHandler getAbstratorHandler() {
        return null;
    }
}
