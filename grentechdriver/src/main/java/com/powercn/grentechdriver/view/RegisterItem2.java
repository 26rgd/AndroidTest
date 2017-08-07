package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractChlidView;

/**
 * Created by Administrator on 2017/8/3.
 */

public class RegisterItem2 extends AbstractChlidView {
    private TextView tvTitle;
    private ImageView ivDetail;
    private TextView tvContent;
    public RegisterItem2(Activity activity, int res) {
        super(activity, res);

        ivDetail=(ImageView)findViewById(R.id.iv_registeritem_detail);
        tvTitle=(TextView)findViewById(R.id.tv_registeritem_title);
        tvContent=(TextView)findViewById(R.id.tv_registeritem_content);
    }

    public void init(String titleContent)
    {
        tvTitle.setText(titleContent);
    }
}
