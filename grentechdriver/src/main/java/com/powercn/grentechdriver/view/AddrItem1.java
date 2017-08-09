package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractChlidView;
import com.powercn.grentechdriver.common.unit.ViewUnit;

/**
 * Created by Administrator on 2017/8/4.
 */

public class AddrItem1 extends AbstractChlidView {
    private ImageView icon;
    private TextView addr;

    public AddrItem1(Activity activity, int res) {
        super(activity, res);


        icon = (ImageView) findViewById(R.id.iv_addr_icon);
        addr = (TextView) findViewById(R.id.tv_addr);
    }

    public AddrItem1(View cview) {
        super(cview);


        icon = (ImageView) findViewById(R.id.iv_addr_icon);
        addr = (TextView) findViewById(R.id.tv_addr);
    }

    public void init(String titleContent, int resid) {
        addr.setText(titleContent);
        icon.setImageResource(resid);
    }
    public void initAddrTextColor(int colorId)
    {
        addr.setTextColor(ViewUnit.getColor(activity.getApplicationContext(),colorId));
    }
}
