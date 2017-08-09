package com.powercn.grentechdriver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.abstration.AbstractBasicActivity;
import com.powercn.grentechdriver.abstration.AbstratorHandler;
import com.powercn.grentechdriver.common.http.HttpRequestTask;
import com.powercn.grentechdriver.entity.LoginInfo;
import com.powercn.grentechdriver.handle.GlobalHandler;
import com.powercn.grentechdriver.view.PasswordItem;
import com.powercn.grentechdriver.view.PhonceCrcItem;
import com.powercn.grentechdriver.view.PhoneItem;
import com.powercn.grentechdriver.view.SelectItem;
import com.powercn.grentechdriver.view.TitleCommon;

import lombok.Getter;

import static android.R.attr.visibility;
import static com.powercn.grentechdriver.R.id.tv_login_register;

/**
 * Created by Administrator on 2017/6/1.
 */


@Getter
public class LoginActivity extends AbstractBasicActivity {
    private TitleCommon titleCommon ;
    private PhoneItem phoneItem;
    private PasswordItem passwordItem;
    private PhonceCrcItem phonceCrcItem;

    private TextView tvFindPassword;
    private Button bt_login_submit;
    private TextView tvRegister;

    private SelectItem typePassword;
    private SelectItem typeCrc;

    private LinearLayout layoutPassword;
    private LinearLayout layoutCrc;

    public String deviceuuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_login);
    }

    @Override
    protected void initView() {
        titleCommon=new TitleCommon(this,R.id.title_login);
        titleCommon.init("登录",this);
        phoneItem =new PhoneItem(this.getWindow().getDecorView());
        passwordItem=new PasswordItem(this.getWindow().getDecorView());
        passwordItem.getPassword().setHint("请输入密码");
        phonceCrcItem=new PhonceCrcItem(this.getWindow().getDecorView());
        phonceCrcItem.getSendCrc().setOnClickListener(this);

        typePassword=new SelectItem(this,R.id.loginpassword);
        typePassword.getTitle().setText("密码登录");
        typePassword.getTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword(true);
                typePassword.doSelect();
                typeCrc.unSelect();
            }
        });

        typeCrc=new SelectItem(this,R.id.logincrc);
        typeCrc.getTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword(false);
                typeCrc.doSelect();
                typePassword.unSelect();
            }
        });
        typeCrc.doSelect();
        typePassword.unSelect();

        tvFindPassword = (TextView) findViewById(R.id.tv_login_findpassword);
        tvRegister = (TextView) findViewById(R.id.tv_login_register);
        bt_login_submit = (Button) findViewById(R.id.bt_login_submit);
        layoutPassword=(LinearLayout)findViewById(R.id.include_login_password);
        layoutCrc=(LinearLayout)findViewById(R.id.include_login_crc);
    }

    private void showPassword(boolean b)
    {
        int visibility1=b==true?View.VISIBLE:View.INVISIBLE;
        layoutPassword.setVisibility(visibility1);
        int visibility2=b==true?View.INVISIBLE:View.VISIBLE;
        layoutCrc.setVisibility(visibility2);
    }
    @Override
    protected void bindListener() {

        tvFindPassword.setOnClickListener(this);
        bt_login_submit.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
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
            case R.id.tv_item_getcrc:

               HttpRequestTask.getSmsCrc(this,phoneItem.getPhone().getText().toString());
                break;
            case R.id.bt_login_submit:
                if(typeCrc.getIsSelect())
                {
                    HttpRequestTask.loginBySms(this,phoneItem.getPhone().getText().toString(),phonceCrcItem.getEtCrc().getText().toString(),deviceuuid);
                }
                else
                {
                    HttpRequestTask.loginBySms(this,phoneItem.getPhone().getText().toString(),phonceCrcItem.getEtCrc().getText().toString(),deviceuuid);
                }
                break;

            case R.id.tv_login_register:
                jumpForResult(Register1Activity.class,1);
                break;
            case R.id.tv_login_findpassword:
                jumpForResult(FindPassword1Activity.class,2);
                break;
        }

    }


}
