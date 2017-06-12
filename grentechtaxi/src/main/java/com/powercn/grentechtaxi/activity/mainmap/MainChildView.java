package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;

import com.powercn.grentechtaxi.MainActivity;

/**
 * Created by Administrator on 2017/5/9.
 */

public abstract class MainChildView extends AbstractChildView implements View.OnClickListener {
    protected String tag=this.getClass().getName();
    protected MainActivity activity;
    public MainChildView(MainActivity activity, int resId) {
        this.activity = activity;
        this.view = findViewById(resId);
        initView();
        bindListener();
        initData();
    }

    protected abstract void initView();

    protected abstract void bindListener();

    protected abstract void initData();

    public void onDestroy() {

    }

    public View findViewById(int resId) {
        return activity.findViewById(resId);
    }

    public View findIncludeViewById(int resId) {
        return getView().findViewById(resId);
    }
    @Override
    public void onClick(View v) {

    }

    public View getView() {
        return view;
    }

    public void setVisibility(int visibility)
    {
        view.setVisibility(visibility);
    }
}
