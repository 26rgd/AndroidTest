package com.powercn.grentechdriver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powercn.grentechdriver.R;
import com.powercn.grentechdriver.adapter.chlid.SettingAdpter;
import com.powercn.grentechdriver.entity.LoginInfo;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.powercn.grentechdriver.R.id.iv_title_back;
import static com.powercn.grentechdriver.R.id.tv_title_back_hint;

/**
 * Created by Administrator on 2017/5/31.
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends AbstractBasicActivity {

    @ViewInject(R.id.tv_title_back_hint)
    private TextView title;
    @ViewInject(iv_title_back)
    private ImageView ivBack;
    @ViewInject(R.id.lv_setting)
    private ListView listView;

    private SettingAdpter settingAdpter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void bindListener() {
    }

    @Override
    protected void initData() {
        title.setText("设置");
        List<SettingAdpter.Item> list=new ArrayList<>();
        list.add(SettingAdpter.Item.about);
        list.add(SettingAdpter.Item.hide1);
        list.add(SettingAdpter.Item.exit);
        settingAdpter=new SettingAdpter(this,list,R.layout.activity_setting_item);
        listView.setAdapter(settingAdpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SettingAdpter.Item item=(SettingAdpter.Item) settingAdpter.getItem(position);
                switch (item)
                {
                    case exit:
                        LoginInfo loginInfo= LoginInfo.readUserLoginInfo(SettingActivity.this);
                        loginInfo.doLoginSuccess=false;
                        LoginInfo.currentLoginSuccess=false;
                        LoginInfo.saveUserLoginInfo(SettingActivity.this,loginInfo);
                         jumpFinish(LoginActivity.class);
                        break;
                }
            }
        });
    }



    @Event(value = R.id.iv_title_back)
    private void back(View v) {
        finish();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}
