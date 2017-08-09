package com.powercn.grentechdriver.view;

import android.app.Activity;
import android.text.InputType;
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
public class PasswordItem extends AbstractChlidView {
    private Boolean isSee;
    private ImageView doSee;
    private EditText password;

    public PasswordItem(Activity activity, int res) {
        super(activity, res);

        initView();
    }

    public PasswordItem(View cview) {
        super(cview);
        initView();

    }

    private void initView() {
        isSee=false;
        doSee = (ImageView) findViewById(R.id.iv_item_see);
        password = (EditText) findViewById(R.id.et_item_password);
        doSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSee=!isSee;
                if(isSee)
                {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    doSee.setImageResource(R.drawable.icon_visible);
                }
                else
                {
                    password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    doSee.setImageResource(R.drawable.icon_invisible);
                }
                password.setSelection(password.getText().length());
            }
        });
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
