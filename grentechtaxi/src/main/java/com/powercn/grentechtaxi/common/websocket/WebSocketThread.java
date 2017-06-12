package com.powercn.grentechtaxi.common.websocket;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.service.WebSocketService;

import org.java_websocket.drafts.Draft_17;

import java.net.URI;

/**
 * Created by Administrator on 2017/6/7.
 */

public class WebSocketThread {
    private String tag=this.getClass().getName();
    private static WebSocketThread webSocketThread;
    private WebSocketTask c = null;
    private Boolean connection=false;
    private String phone;

    private WebSocketThread() {
    }


    public static  WebSocketThread getInstance()
    {
        if (webSocketThread==null)
            webSocketThread=new WebSocketThread();

        return  webSocketThread;
    }
    public void start(String phone)
    {
        this.phone=phone;
        onOpen();
    }


    private void onOpen() {
        try {
            // WebSocketTask c = new WebSocketTask( new URI( "ws://192.168.5.42:8080/grentechTaxiWx/webSocketServer" ), new Draft_17() );
            c = new WebSocketTask(new URI(HttpRequestTask.webSocketurl), new Draft_17());
            c.connectBlocking();
            connection=true;
            sendWebSocket("");
        } catch (Exception e) {
            connection=false;
            e.printStackTrace();
        }
    }
    public void setOnReceiver(WebSocketTask.onReceiver onReceiver)
    {

        c.setOnReceiver(onReceiver);
    }

    private void sendWebSocket(String msg) {
        try {
            c.send(phone);
            StringUnit.println(tag,"注册手机号: "+phone);
        } catch (Exception e) {
            e.printStackTrace();
            connection=false;
        }
    }
}
