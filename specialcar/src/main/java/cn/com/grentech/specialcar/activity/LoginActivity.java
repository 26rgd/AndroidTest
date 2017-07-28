package cn.com.grentech.specialcar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.SysApplication;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.unit.FileUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.GpsInfo;
import cn.com.grentech.specialcar.entity.LoadLine;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.handler.LoginMessageHandler;
import cn.com.grentech.specialcar.service.ServiceAddr;
import cn.com.grentech.specialcar.service.ServiceGPS;
import cn.com.grentech.specialcar.service.ServiceMoitor;
import cn.com.grentech.specialcar.service.ServiceUpfile;
import cn.com.grentech.specialcar.sqllite.SQLiteHelper;
import lombok.Getter;

import static cn.com.grentech.specialcar.entity.LoginInfo.readUserLoginInfo;

/**
 * Created by Administrator on 2017/6/14.
 */

public class LoginActivity extends AbstractBasicActivity {
    private ImageView ivBack;
    private Button btLogin;
    private TextView tvFindPassword;
    @Getter
    private AutoCompleteTextView tvUserName;
    @Getter
    private AutoCompleteTextView tvPassword;
    private static AbstractHandler abstratorHandler=null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_loginnew);
        startService(ServiceUpfile.class);
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
        SysApplication.getInstance().getSqLiteHelper().deleteTenDaysAgo();
        StringUnit.println(tag,"GPS纪录"+SysApplication.getInstance().getSqLiteHelper().getCount()+"条");
        List<GpsInfo> list=SysApplication.getInstance().getSqLiteHelper().getGpsInfoList(715);
        for(GpsInfo gpsInfo:list)
        {
            StringUnit.println(tag,gpsInfo.toString());
        }

    }


    @Override
    protected void initData() {
        abstratorHandler=new LoginMessageHandler(this);
       LoginInfo userinfo= LoginInfo.readUserLoginInfo(this.getApplicationContext());
        tvPassword.setText(userinfo.password);
        tvUserName.setText(userinfo.phone);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
            } else {
                    showToast("GPS权限被禁用无法计算位置..请开启此权限");
                StringUnit.println(tag,"GPS权限被禁用无法计算位置..请开启此权限");
            }

        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
            } else {
                showToast("存储权限被禁用无法准备计算总距离..请开启此权限");
                StringUnit.println(tag,"存储权限被禁用无法准备计算总距离..请开启此权限");
            }

        }
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

}
