package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractChlidView;
import com.powercn.grentechdriver.common.unit.ViewUnit;

import lombok.Getter;

/**
 * Created by Administrator on 2017/8/8.
 */
@Getter
public class PhonceCrcItem extends AbstractChlidView {
    private EditText etCrc;
    private TextView sendCrc;

    public PhonceCrcItem(Activity activity, int res) {
        super(activity, res);
        initView();
    }

    public PhonceCrcItem(View cview) {
        super(cview);
        initView();

    }

    private void initView() {
        etCrc = (EditText) findViewById(R.id.et_item_crc);
        sendCrc = (TextView) findViewById(R.id.tv_item_getcrc);
    }


}
