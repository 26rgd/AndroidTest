package cn.com.grentech.specialcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.handler.PasswordMessageHandle;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.unit.StringUnit;

/**
 * Created by Administrator on 2017/6/15.
 */

public class EditPasswordActivity extends AbstractBasicActivity {
    public final static int find_password=88;
    public final static int edit_password=89;
    private static AbstractHandler abstratorHandler = null;
    private ImageView ivBack;
    private TextView tvUserName;
    private TextView tvPass1;
    private TextView tvPass2;
    private TextView tvCrc;
    private TextView tvTitle;
    private Button btOk;
    private Button btCrc;
    private TimeCount timeCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_passwrod);
    }

    @Override
    protected void initView() {

        ivBack = (ImageView) findViewById(R.id.iv_title_back);
        btOk = (Button) findViewById(R.id.bt_password_ok);
        btCrc = (Button) findViewById(R.id.bt_getcrc);
        tvUserName = (TextView) findViewById(R.id.tv_username);
        tvPass1 = (TextView) findViewById(R.id.tv_password1);
        tvPass2 = (TextView) findViewById(R.id.tv_password2);
        tvCrc = (TextView) findViewById(R.id.tv_smscrc);
        tvTitle = (TextView) findViewById(R.id.tv_title_back_hint);

    }

    @Override
    protected void bindListener() {
        btOk.setOnClickListener(this);
        btCrc.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        abstratorHandler=new PasswordMessageHandle(this);
        timeCount = new TimeCount(120000, 1000);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle==null)
        {
            tvTitle.setText("密码");
        }
        int requestCode =bundle.getInt("requestCode");
        if(requestCode==find_password)
        {
            tvTitle.setText("找回密码");
        }else
            tvTitle.setText("修改密码");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_password_ok:

                findPassword();
                break;
            case R.id.bt_getcrc:
                String phone = tvUserName.getText().toString();
                if (StringUnit.isEmpty(phone)) {
                    showToast("号码不能为空");
                    return;
                }
                timeCount.start();
                HttpRequestTask.getSmsCrc(this, phone);
                break;
        }
    }

    private void findPassword() {
        String phone = tvUserName.getText().toString();
        String pass1 = tvPass1.getText().toString();
        String pass2 = tvPass2.getText().toString();
        String crc = tvCrc.getText().toString();
        if (StringUnit.isEmpty(phone) || StringUnit.isEmpty(pass1) || StringUnit.isEmpty(pass2) || StringUnit.isEmpty(crc)) {
            showToast("选项不能为空");
            return;
        }
        if (!pass1.equals(pass2)) {
            showToast("输入密码不一致");
            return;
        }
        HttpRequestTask.resetPassword(this, phone, pass1, crc);
    }
    @Override
    public AbstractHandler getAbstratorHandler() {
        return   abstratorHandler;
    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btCrc.setClickable(false);
            btCrc.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btCrc.setClickable(true);
            btCrc.setText("重新验证");
        }
    }
}
