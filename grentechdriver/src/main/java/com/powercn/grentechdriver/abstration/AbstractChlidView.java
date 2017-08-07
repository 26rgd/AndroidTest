package com.powercn.grentechdriver.abstration;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.SysApplication;
import com.powercn.grentechdriver.common.unit.ViewUnit;

/**
 * Created by Administrator on 2017/8/3.
 */

public class AbstractChlidView implements View.OnClickListener {
    protected Activity activity;
    protected Context context;
    protected View view;

    public AbstractChlidView(Activity activity, int res) {

        this.activity = activity;
        view = activity.findViewById(res);
        this.context = activity.getApplicationContext();
    }
    public AbstractChlidView(View view) {
        this.view = view;
    }

    protected View findViewById(int resId)
    {
        return view.findViewById(resId);
    }
    public void initTextSize(TextView view, int sizeId) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, ViewUnit.getSizeFloat(SysApplication.getInstance().getContext(), sizeId));
    }

    public void initTextColor(TextView view, int colorId) {

        view.setTextColor(ViewUnit.getColor(SysApplication.getInstance().getContext(), colorId));
    }

    @Override
    public void onClick(View v) {

    }
}
