package cn.com.grentech.specialcar.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.handler.LoginMessageHandler;
import cn.com.grentech.specialcar.service.ServiceGPS;
import cn.com.grentech.specialcar.service.ServiceMoitor;
import lombok.Getter;

/**
 * Created by Administrator on 2017/6/14.
 */

public class LoginActivity extends AbstractBasicActivity {
    private ImageView ivBack;
    private Button btLogin;
    private TextView tvFindPassword;
    @Getter
    private AutoCompleteTextView tvUserName;
    private AutoCompleteTextView tvPassword;
    private static AbstractHandler abstratorHandler=null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_loginnew);
    }

    @Override
    protected void initView() {
        ivBack=(ImageView)findViewById(R.id.iv_login_back);
        ivBack.setVisibility(View.GONE);
        btLogin=(Button)findViewById(R.id.bt_login_submit);
        tvUserName=(AutoCompleteTextView)findViewById(R.id.tv_username);
        tvPassword=(AutoCompleteTextView)findViewById(R.id.tv_password);
        tvFindPassword=(TextView)findViewById(R.id.tv_findpassword);
    }

    @Override
    protected void bindListener() {
        tvFindPassword.setOnClickListener(this);
        btLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        abstratorHandler=new LoginMessageHandler(this);
        startService(ServiceMoitor.class);
        startService(ServiceGPS.class);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_login_submit:
                HttpRequestTask.loginByPassword(this,tvUserName.getText().toString(),tvPassword.getText().toString());
                break;
            case R.id.tv_findpassword:
                jumpForResult(EditPasswordActivity.class,EditPasswordActivity.find_password);
                break;
        }
    }
    @Override
    public AbstractHandler getAbstratorHandler() {
        return   abstratorHandler;
    }
//    public  void sendHandleMessage(String key, String content, Object object) {
//        try {
//            Bundle bundle = new Bundle();
//            bundle.putString(key, content);
//            Message msg = new Message();
//            msg.what = 0;
//            msg.setData(bundle);
//            msg.obj = object;
//            abstratorHandler.sendMessage(msg);
//        } catch (Exception e) {
//        }
//    }
}
