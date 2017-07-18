package com.powercn.grentechdriver.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.common.http.HttpRequestTask;
import com.powercn.grentechdriver.entity.LoginInfo;
import com.powercn.grentechdriver.handle.GlobalHandler;
import com.powercn.grentechdriver.handle.LoginMessageHandler;

import lombok.Getter;

/**
 * Created by Administrator on 2017/6/1.
 */


@Getter
public class LoginActivity extends AbstractBasicActivity {
    private ImageView iv_title_back;
    private AutoCompleteTextView tv_login_phone;
    private AutoCompleteTextView tv_login_crc;
    private Button bt_login_crc_send;
    private Button bt_login_submit;

    public String deviceuuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_loginnew);
    }

    @Override
    protected void initView() {
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        tv_login_phone = (AutoCompleteTextView) findViewById(R.id.tv_login_phone);
        tv_login_crc = (AutoCompleteTextView) findViewById(R.id.tv_login_crc);
        bt_login_crc_send = (Button) findViewById(R.id.bt_login_crc_send);
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
                HttpRequestTask.loginBySms(this,tv_login_phone.getText().toString(),tv_login_crc.getText().toString(),deviceuuid);
                break;
        }

    }


}
