package com.powercn.grentechtaxi.activity.mainmap;

import android.app.Activity;
import android.view.View;

import com.powercn.grentechtaxi.activity.OrderInfoActivity;

/**
 * Created by Administrator on 2017/6/9.
 */

public abstract class AbstractChildView {
    protected View view;
    public abstract View findViewById(int resId);
    public abstract View findIncludeViewById(int resId);

    public View getView() {
        return view;
    }
}
