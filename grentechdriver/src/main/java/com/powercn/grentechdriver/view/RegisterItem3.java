package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractChlidView;

/**
 * Created by Administrator on 2017/8/4.
 */

public class RegisterItem3 extends AbstractChlidView {

    private TextView tvTitle;
    private TextView tvContent;
    public RegisterItem3(Activity activity, int res) {
        super(activity, res);


        tvTitle=(TextView)findViewById(R.id.tv_registeritem3_title);
        tvContent=(TextView)findViewById(R.id.tv_registeritem3_content);
    }
    public void init(String titleContent,String hint)
    {
        tvTitle.setText(titleContent);
        tvContent.setHint(hint);
    }
}
