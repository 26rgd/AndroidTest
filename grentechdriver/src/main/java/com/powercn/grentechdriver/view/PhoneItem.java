package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractChlidView;
import com.powercn.grentechdriver.common.unit.ViewUnit;

import lombok.Getter;

/**
 * Created by Administrator on 2017/8/8.
 */

public class PhoneItem extends AbstractChlidView {
    private ImageView icon;
    @Getter
    private TextView phone;

    public PhoneItem(Activity activity, int res) {
        super(activity, res);

        initView();
    }

    public PhoneItem(View cview) {
        super(cview);
        initView();

    }

    private void initView() {

        phone = (TextView) findViewById(R.id.tv_item_phone);
    }

//    public void init(String titleContent, int resid) {
//        addr.setText(titleContent);
//        icon.setImageResource(resid);
//    }
//
//    public void initAddrTextColor(int colorId) {
//        addr.setTextColor(ViewUnit.getColor(activity.getApplicationContext(), colorId));
//    }
}
