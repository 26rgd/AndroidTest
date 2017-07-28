package com.powercn.grentechdriver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.common.http.HttpRequestTask;
import com.powercn.grentechdriver.entity.LoginInfo;
import com.powercn.grentechdriver.handle.GlobalHandler;

/**
 * Created by Administrator on 2017/7/27.
 */

public class Register1Activity extends AbstractBasicActivity {
    private ImageView iv_title_back;
    private EditText tv_login_phone;
    private EditText tv_login_crc;
    private TextView bt_login_crc_send;
    private TextView tv_title_back_hint;
    private TextView tv_login_register;
    private Button bt_login_submit;


    public String deviceuuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_loginnew);
    }

    @Override
    protected void initView() {
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        tv_login_phone = (EditText) findViewById(R.id.tv_login_phone);
        tv_login_crc = (EditText) findViewById(R.id.tv_login_crc);
        bt_login_crc_send = (TextView) findViewById(R.id.bt_login_crc_send);
        tv_title_back_hint = (TextView) findViewById(R.id.tv_title_back_hint);
        tv_login_register = (TextView) findViewById(R.id.tv_login_register);
        bt_login_submit = (Button) findViewById(R.id.bt_login_submit);
    }

    @Override
    protected void bindListener() {
        iv_title_back.setOnClickListener(this);
        bt_login_crc_send.setOnClickListener(this);
        bt_login_submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        deviceuuid = LoginInfo.getUuid(this);
        tv_title_back_hint.setText("注册");
        bt_login_submit.setText("下一步");
        tv_login_register.setVisibility(View.INVISIBLE);

    }

    @Override
    public AbstratorHandler getAbstratorHandler() {
        return GlobalHandler.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:

                finish();
                break;
            case R.id.bt_login_crc_send:

                HttpRequestTask.getSmsCrc(this,tv_login_phone.getText().toString());
                break;
            case R.id.bt_login_submit:
                jumpForResult(Register2Activity.class,11);
               // HttpRequestTask.loginBySms(this,tv_login_phone.getText().toString(),tv_login_crc.getText().toString(),deviceuuid);
                break;
        }

    }
}
