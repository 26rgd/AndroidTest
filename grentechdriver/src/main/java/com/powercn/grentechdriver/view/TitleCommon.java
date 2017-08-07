package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractChlidView;


/**
 * Created by Administrator on 2017/8/4.
 */

public class TitleCommon extends AbstractChlidView {
    private TextView tvTitle;
    private ImageView ivBack;
    public TitleCommon(Activity activity, int res) {
        super(activity, res);


        tvTitle=(TextView)findViewById(R.id.tv_title_back_hint);
        ivBack=(ImageView)findViewById(R.id.iv_title_back);
    }
    public void init(String title, @Nullable View.OnClickListener l)
    {
        tvTitle.setText(title);
        ivBack.setOnClickListener(l);
    }
}
