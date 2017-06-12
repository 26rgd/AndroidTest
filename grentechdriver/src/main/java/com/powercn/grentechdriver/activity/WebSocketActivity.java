package com.powercn.grentechdriver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.powercn.grentechdriver.common.websocket.WebSocketTask;

import org.java_websocket.drafts.Draft_17;

import java.net.URI;


/**
 * Created by Administrator on 2017/4/27.
 */

public class WebSocketActivity extends AbstractBasicActivity {

    private EditText et_websocket_info;
    private Button bt_websocket_open;
    private Button bt_websocket_send;
    WebSocketTask c = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState, R.layout.activity_websocket);
    }

    @Override
    protected void initView() {
//        et_websocket_info = (EditText) findViewById(R.id.et_websocket_info);
//        bt_websocket_open = (Button) findViewById(R.id.bt_websocket_open);
//        bt_websocket_send = (Button) findViewById(R.id.bt_websocket_send);
    }

    @Override
    protected void bindListener() {
        bt_websocket_open.setOnClickListener(this);
        bt_websocket_send.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.bt_websocket_open:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        onOpen();
//                    }
//                }).start();
//                break;
//            case R.id.bt_websocket_send:
//                sendWebSocket("");
//                break;
//        }
    }


    private void onOpen() {
        try {
            // WebSocketTask c = new WebSocketTask( new URI( "ws://192.168.5.42:8080/grentechdriverWx/webSocketServer" ), new Draft_17() );
            c = new WebSocketTask(new URI("ws://192.168.5.25/grentechdriverWx/webSocketServer"), new Draft_17());
            c.connectBlocking();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWebSocket(String msg) {
        try {
            c.send("13510197040");
            c.send("测试--handshake");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
