package com.powercn.grentechdriver.activity.mainmap;

import android.view.View;

import com.powercn.grentechdriver.MainActivity;

/**
 * Created by Administrator on 2017/5/9.
 */

public abstract class AbstractChildView implements View.OnClickListener {
    protected MainActivity activity;
    protected View view;

    public AbstractChildView(MainActivity activity, int resId) {
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

    protected View findViewById(int resId) {
        return activity.findViewById(resId);
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
