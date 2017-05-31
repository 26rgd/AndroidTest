package com.powercn.grentechtaxi.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.view.UserDialog;

import static com.powercn.grentechtaxi.R.id.tv_login_forgetpassword;

/**
 * Created by Administrator on 2017/5/4.
 */

public class LoginDialog extends UserDialog implements View.OnClickListener {
    public TextView tv_login_sms_login;
    public TextView tv_login_password_login;
    public TextView  tv_login_forgetpassword;
    public TextInputLayout form_login_form1;
    public FrameLayout form_login_form2;
    public Button bt_login_submit;
    public ImageView iv_login_close;
    public LoginDialog(Context context, int dialogres, String title) {
        super(context, dialogres, title);
    }

    @Override
    protected void initView() {
        tv_login_sms_login=(TextView)findViewById(R.id.tv_login_sms_login);
        tv_login_password_login=(TextView)findViewById(R.id.tv_login_password_login);
        tv_login_forgetpassword=(TextView)findViewById(R.id.tv_login_forgetpassword);
        form_login_form1=(TextInputLayout)findViewById(R.id.form_login_form1);
        form_login_form2=(FrameLayout)findViewById(R.id.form_login_form2);
        bt_login_submit=(Button)findViewById(R.id.bt_login_submit);
        iv_login_close=(ImageView)findViewById(R.id.iv_login_close);
    }

    @Override
    protected void bindListener() {
        tv_login_sms_login.setOnClickListener(this);
        tv_login_password_login.setOnClickListener(this);
        tv_login_forgetpassword.setOnClickListener(this);
        bt_login_submit.setOnClickListener(this);
        iv_login_close.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        form_login_form1.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_sms_login:
                iniMenu();
                form_login_form2.setVisibility(View.VISIBLE);
                tv_login_sms_login.setTextColor(getContext().getResources().getColor(R.color.GREEN));
                break;
            case R.id.tv_login_password_login:
                iniMenu();
                form_login_form1.setVisibility(View.VISIBLE);
                tv_login_forgetpassword.setVisibility(View.VISIBLE);
                tv_login_password_login.setTextColor(getContext().getResources().getColor(R.color.GREEN));
                break;
            case R.id.iv_login_close:
               this.dismiss();
                break;
        }
    }
    private void iniMenu()
    {
        form_login_form1.setVisibility(View.GONE);
        form_login_form2.setVisibility(View.GONE);
        tv_login_forgetpassword.setVisibility(View.GONE);
        tv_login_sms_login.setTextColor(getContext().getResources().getColor(R.color.BLACK));
        tv_login_password_login.setTextColor(getContext().getResources().getColor(R.color.BLACK));
    }

    @Override
    public void iniPositiveButton() {

    }
}
