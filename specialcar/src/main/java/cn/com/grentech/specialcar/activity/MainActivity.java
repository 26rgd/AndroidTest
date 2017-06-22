package cn.com.grentech.specialcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.com.grentech.specialcar.R;
import cn.com.grentech.specialcar.abstraction.AbstractBasicActivity;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.adapter.NotFinshAdapter;
import cn.com.grentech.specialcar.adapter.PopupWindowAdapter;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.unit.GsonUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.common.unit.ViewUnit;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.entity.Order;
import cn.com.grentech.specialcar.handler.MainMessageHandler;
import lombok.Getter;

public class MainActivity extends AbstractBasicActivity {
    private static AbstractHandler abstratorHandler = null;
    private ListView listView;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvTopLine;
    @Getter
    private SwipeRefreshLayout refresh;
    @Getter
    private NotFinshAdapter notFinshAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StringUnit.println(tag, GsonUnit.toJson(savedInstanceState));
        super.onCreate(savedInstanceState, R.layout.activity_main);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.lv_nofinsh);
        ivBack = (ImageView) findViewById(R.id.iv_title_back);
        tvTitle = (TextView) findViewById(R.id.tv_title_back_hint);
        tvTopLine = (TextView) findViewById(R.id.tv_top_line);
        ivBack.setImageResource(R.drawable.icon_menu);
        refresh   = (SwipeRefreshLayout) findViewById(R.id.contanier_mainrefres);

    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpRequestTask.getNotFinsh(MainActivity.this, LoginInfo.loginInfo.phone, 0, 20);
                postRefresh(5000);
            }
        });
    }

    @Override
    protected void initData() {

        abstratorHandler = new MainMessageHandler(this);
        tvTitle.setText("未完成订单");
        notFinshAdapter = new NotFinshAdapter(this, null, R.layout.listview_item_nofinsh);
        HttpRequestTask.getNotFinsh(this, LoginInfo.loginInfo.phone, 0, 20);
        listView.setAdapter(notFinshAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order info = (Order) notFinshAdapter.getItem(position);
                jumpForResult(OrderDetailActivity.class, OrderDetailActivity.UnFinish_Order, "order", info);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                showPopupWindow(tvTopLine);
                Intent intent=new Intent();
                intent.setAction("com.xiazdong");
                MainActivity.this.sendBroadcast(intent);
                break;
        }
    }

    private void showPopupWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.menu, null);
        ListView listView = (ListView) contentView.findViewById(R.id.lv_mainmap_popupwindows);
        final PopupWindowAdapter popupWindowAdapter = new PopupWindowAdapter(this, null, R.layout.listview_item_menu);
        listView.setAdapter(popupWindowAdapter);
        listView.setDivider(null);
        int width = ViewUnit.getDisplayWidth(this) * 7 / 15;
        final PopupWindow popupWindow = new PopupWindow(contentView,
                width, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupWindowAdapter.PopuWindowInfo popuWindowInfo = (PopupWindowAdapter.PopuWindowInfo) popupWindowAdapter.getItem(position);
                switch (popuWindowInfo) {
                    case Order:
                        jumpForResult(OrderListActivity.class, 55);
                        break;
                    case UserInfo:
                        jumpForResult(DriverInfoActivity.class, 55);
                        break;
                    case Password:
                        jumpForResult(EditPasswordActivity.class, EditPasswordActivity.edit_password);
                        break;
                    case LoginOut:
                        HttpRequestTask.logout(MainActivity.this);
                        jumpFinish(LoginActivity.class);
                        break;
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(ViewUnit.getDrawable(this, R.drawable.btn_back));
        popupWindow.showAsDropDown(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getRefresh().setRefreshing(true);
        postRefresh(2000);
        HttpRequestTask.getNotFinsh(this, LoginInfo.loginInfo.phone, 0, 20);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void postRefresh(final int timeout)
    {
        getRefresh().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRefresh().setRefreshing(false);
            }
        }, timeout);
    }
    @Override
    public AbstractHandler getAbstratorHandler() {
        return   abstratorHandler;
    }
    //    public void sendHandleMessage(String key, String content, Object object) {
//        try {
//            Bundle bundle = new Bundle();
//            bundle.putString(key, content);
//            Message msg = new Message();
//            msg.what = 0;
//            msg.setData(bundle);
//            msg.obj = object;
//            abstratorHandler.sendMessage(msg);
//        } catch (Exception e) {
//            StringUnit.println(tag,"sendHandleMessage Error");
//        }
//    }
}
