package com.powercn.grentechdriver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.adapter.MineAdapter;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MineActivity extends AbstractBasicActivity {

    private ListView listView;
    private MineAdapter mineAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_mine);
    }
    @Override
    protected void initView() {
        listView=(ListView)findViewById(R.id.lv_mine);
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void initData() {
        mineAdapter=new MineAdapter(this,null,R.layout.mainside_popupwindow_item);
        listView.setAdapter(mineAdapter);
    }

    @Override
    public AbstratorHandler getAbstratorHandler() {
        return null;
    }
}
