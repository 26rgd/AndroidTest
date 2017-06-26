package cn.com.grentech.specialcar.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.adapter.DriverAdapter;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.entity.DriverInfo;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.handler.DriverInfoMessageHandler;
import lombok.Getter;

/**
 * Created by Administrator on 2017/6/15.
 */

public class DriverInfoActivity extends AbstractBasicActivity {
    private static AbstractHandler abstratorHandler=null;
    private ListView listView;
    private ImageView ivBack;
    private TextView tvSub;
    @Getter
    private DriverAdapter driverAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_driverinfo);
    }
    @Override
    protected void initView() {
        listView=(ListView)findViewById(R.id.lv_driverinfo);
        ivBack=(ImageView)findViewById(R.id.iv_driverinfo_back);
        tvSub=(TextView)findViewById(R.id.tv_driverinfo_sub);
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        tvSub.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        driverAdapter=new DriverAdapter(this,null,R.layout.listview_item_driverinfo);
        abstratorHandler=new DriverInfoMessageHandler(this);
        listView.setAdapter(driverAdapter);
        HttpRequestTask.checkSession(this, LoginInfo.loginInfo.phone);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_driverinfo_back:
                finish();
                break;
            case R.id.tv_driverinfo_sub:
                saveDriver();
                break;
        }
    }
    private void saveDriver()
    {
        String name= DriverInfo.fname.getValue();
        String phone= DriverInfo.phone.getValue();
        String address= DriverInfo.address.getValue();
        String licenseNo= DriverInfo.licenseNo.getValue();
        String carNo= DriverInfo.carNo.getValue();
        String carType= DriverInfo.carType.getValue();
        String carRow= DriverInfo.carRow.getValue();
        HttpRequestTask.updateDriver(this,phone,name,licenseNo,address,carNo,carType,Integer.valueOf(carRow));
    }

    @Override
    public AbstractHandler getAbstratorHandler() {
        return   abstratorHandler;
    }

}
