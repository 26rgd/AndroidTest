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

import static com.powercn.grentechdriver.R.id.bt_login_crc_send;
import static com.powercn.grentechdriver.R.id.tv_login_phone;
import static com.powercn.grentechdriver.R.id.tv_login_register;

/**
 * Created by Administrator on 2017/7/27.
 */

public class Register2Activity extends AbstractBasicActivity {
    private ImageView iv_title_back;
    private TextView tv_title_back_hint;

    private Button bt_login_submit;


    public String deviceuuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_register2);
    }

    @Override
    protected void initView() {
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        tv_title_back_hint = (TextView) findViewById(R.id.tv_title_back_hint);
        bt_login_submit = (Button) findViewById(R.id.bt_login_submit);
    }

    @Override
    protected void bindListener() {
        iv_title_back.setOnClickListener(this);
        bt_login_submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        deviceuuid = LoginInfo.getUuid(this);
        tv_title_back_hint.setText("注册");
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
            case bt_login_crc_send:


                break;
            case R.id.bt_login_submit:
                jumpFinish(MineActivity.class);
                // HttpRequestTask.loginBySms(this,tv_login_phone.getText().toString(),tv_login_crc.getText().toString(),deviceuuid);
                break;
        }

    }
}
