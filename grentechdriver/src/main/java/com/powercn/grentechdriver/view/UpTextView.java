package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.SysApplication;
import com.powercn.grentechdriver.abstration.AbstractChlidView;
import com.powercn.grentechdriver.common.unit.ViewUnit;

import lombok.Getter;

/**
 * Created by Administrator on 2017/8/2.
 */

public class UpTextView  extends AbstractChlidView{
    private LinearLayout layout;
    @Getter
    private TextView line1;
    @Getter
    private TextView line2;
    public UpTextView(Activity activity,int res) {
        super(activity,res);
        this.activity=activity;
//        view=activity.findViewById(res);
        layout=(LinearLayout)findViewById(R.id.layout_uptextview);
        line1=(TextView)findViewById(R.id.tv_up_line1);
        line2=(TextView)findViewById(R.id.tv_up_line2);
    }
    public void setLine1TextColor(int colorId)
    {
        line1.setTextColor(ViewUnit.getColor(SysApplication.getInstance().getContext(),colorId));
    }
    public void setLine2TextColor(int colorId)
    {
        line2.setTextColor(ViewUnit.getColor(SysApplication.getInstance().getContext(),colorId));
    }

    public void setLine1TextSize(int sizeId)
    {
        line1.setTextSize(TypedValue.COMPLEX_UNIT_PX,ViewUnit.getSizeFloat(SysApplication.getInstance().getContext(),sizeId));

    }

    public void setLine2TextSize(int sizeId)
    {
        line2.setTextSize(TypedValue.COMPLEX_UNIT_PX,ViewUnit.getSizeFloat(SysApplication.getInstance().getContext(),sizeId));

    }
    public void setBakcGround(int resid)
    {
        if(layout!=null)
        ViewUnit.setBackgroundDrawable(activity,layout,resid);
    }

    public void initLine1(String content,int sizeId,int colorid)
    {
        line1.setText(content);
        setLine1TextColor(colorid);
        setLine1TextSize(sizeId);
    }

    public void initLine2(String content,int sizeId,int colorid)
    {
        line2.setText(content);
        setLine2TextColor(colorid);
        setLine2TextSize(sizeId);
    }
}
