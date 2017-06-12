package com.powercn.grentechtaxi.activity.mainmap;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;

import lombok.Getter;

/**
 * Created by Administrator on 2017/5/10.
 */

@Getter
public class LoginView extends MainChildView {
    private ImageView iv_title_back;
    private AutoCompleteTextView tv_login_phone;
    private AutoCompleteTextView tv_login_crc;
    private Button bt_login_crc_send;
    private Button bt_login_submit;

    public LoginView(MainActivity activity, int resId) {
        super(activity, resId);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                activity.hideSoft();
                getView().setVisibility(View.GONE);
                break;
            case R.id.bt_login_crc_send:

                HttpRequestTask.getSmsCrc(tv_login_phone.getText().toString());
                break;
            case R.id.bt_login_submit:
                activity.hideSoft();
                HttpRequestTask.loginBySms(tv_login_phone.getText().toString(),tv_login_crc.getText().toString(),activity.deviceuuid);
                break;
        }

    }
}
