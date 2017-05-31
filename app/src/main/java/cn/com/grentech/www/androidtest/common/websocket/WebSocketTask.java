package cn.com.grentech.www.androidtest.common.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import cn.com.grentech.www.androidtest.common.unit.StringUnit;

/**
 * Created by Administrator on 2017/4/27.
 */

public class WebSocketTask extends WebSocketClient {


    public WebSocketTask(URI serverUri , Draft draft ) {
        super( serverUri, draft );
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println( "开流--opened connection" );

    }

    @Override
    public void onMessage(String s) {
        StringUnit.println(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

        System.out.println( "关流--Connection closed by "  );
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
