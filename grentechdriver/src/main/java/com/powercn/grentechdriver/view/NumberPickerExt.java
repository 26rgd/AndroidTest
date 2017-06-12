package com.powercn.grentechdriver.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.common.unit.ViewUnit;

/**
 * Created by Administrator on 2017/5/12.
 */

public class NumberPickerExt extends NumberPicker {
    public NumberPickerExt(Context context) {
        super(context);
    }

    public NumberPickerExt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberPickerExt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public NumberPickerExt(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        updateView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    public void updateView(View view)
    {
        if(view instanceof EditText)
        {
            ((EditText) view).setTextColor(ViewUnit.getColor(getContext(),R.color.BLACK));

        }
    }
}
