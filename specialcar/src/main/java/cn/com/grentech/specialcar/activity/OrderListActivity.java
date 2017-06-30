package cn.com.grentech.specialcar.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.adapter.OrderListAdapter;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.handler.OrderListMessageHandler;
import lombok.Getter;

import static android.view.KeyEvent.KEYCODE_HOME;

/**
 * Created by Administrator on 2017/6/15.
 */

public class OrderListActivity extends AbstractBasicActivity {
   private static AbstractHandler abstratorHandler=null;
    private ListView listView;
    private ImageView ivBack;
    private TextView tvTitle;
    @Getter
    private OrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_orderlist);
    }
    @Override
    protected void initView() {
        listView=(ListView)findViewById(R.id.lv_orderlist);
        ivBack=(ImageView)findViewById(R.id.iv_title_back);
        tvTitle=(TextView)findViewById(R.id.tv_title_back_hint) ;
        tvTitle.setText("历史订单");
    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        abstratorHandler=new OrderListMessageHandler(this);
        orderListAdapter=new OrderListAdapter(this,null,R.layout.listview_item_nofinsh);
        listView.setAdapter(orderListAdapter);
        HttpRequestTask.getOrderList(this, LoginInfo.loginInfo.phone,0,40);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order info=(Order) orderListAdapter.getItem(position);
                jumpForResult(HisOrderDetailActivity.class,HisOrderDetailActivity.Finish_Order,"order",info);

            }
        });
    }

    @Override
    public AbstractHandler getAbstratorHandler() {
        return   abstratorHandler;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_title_back:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

}
