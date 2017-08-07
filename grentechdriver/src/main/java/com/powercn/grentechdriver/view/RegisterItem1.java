package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractChlidView;



/**
 * Created by Administrator on 2017/8/3.
 */

public class RegisterItem1 extends AbstractChlidView {
    private TextView tvTitle;
    private TextView tvContent;
    public RegisterItem1(Activity activity, int res) {
        super(activity, res);


        tvTitle=(TextView)findViewById(R.id.tv_registeritem1_title);
        tvContent=(TextView)findViewById(R.id.tv_registeritem1_content);
    }
    public void init(String titleContent,String hint)
    {
        tvTitle.setText(titleContent);
        tvContent.setHint(hint);
    }
}
