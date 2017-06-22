package com.powercn.grentechtaxi.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.common.unit.ViewUnit;

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

    public void setValues(String [] line)
    {
        if(line==null)
        {
            return;
        }
        String [] vs=this.getDisplayedValues();
        if(vs==null)
        {      this.setDisplayedValues(line);
                this.setMinValue(0);
                this.setMaxValue(line.length-1);


        }else
        {
            int i=this.getValue();
            String s=this.getDisplayedValues()[i];
            if(vs.length>=line.length)
            {
               // init();
                this.setMinValue(0);
                this.setMaxValue(line.length-1);
                this.setDisplayedValues(line);
            }else
            {
                //init();
                this.setDisplayedValues(line);
                this.setMinValue(0);
                this.setMaxValue(line.length-1);
            }
            for(int n=0;n<this.getDisplayedValues().length;n++)
            {
                String li=this.getDisplayedValues()[n];
                this.setValue(0);
                if(li.equals(s))
                {
                    this.setValue(n);
                    break;
                }
            }

        }
    }

    public String getCurDisplay()
    {
        int i=this.getValue();
        String display=this.getDisplayedValues()[i];
        return display;
    }
    public void init()
    {
        this.setMinValue(0);
        this.setMaxValue(0);
        this.setDisplayedValues(null);
    }
}
