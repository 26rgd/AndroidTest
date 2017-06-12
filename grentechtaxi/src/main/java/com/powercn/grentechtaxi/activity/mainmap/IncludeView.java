package com.powercn.grentechtaxi.activity.mainmap;

import android.app.Activity;
import android.view.View;

import com.powercn.grentechtaxi.MainActivity;

/**
 * Created by Administrator on 2017/6/9.
 */

public class IncludeView extends AbstractChildView {
    protected String tag=this.getClass().getName();
    protected Activity activity;

    public IncludeView(Activity activity, int resId) {
        this.activity = activity;
        this.view=findViewById(resId);
    }

    @Override
    public View findViewById(int resId) {
        return activity.findViewById(resId);
    }

    @Override
    public View findIncludeViewById(int resId) {
          return getView().findViewById(resId);
    }
}
