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

public class AnswerOrderActivity extends AbstractBasicActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_answerorder);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public AbstratorHandler getAbstratorHandler() {
        return null;
    }
}

