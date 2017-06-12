package com.powercn.grentechdriver.common.websocket;

import com.powercn.grentechdriver.common.unit.StringUnit;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


/**
 * Created by Administrator on 2017/4/27.
 */

public class WebSocketTask extends WebSocketClient {


    public WebSocketTask(URI serverUri , Draft draft ) {
        super( serverUri, draft );
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        StringUnit.println( "开流--opened connection" );

    }

    @Override
    public void onMessage(String s) {
        StringUnit.println(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

        StringUnit.println( "关流--Connection closed by "  );
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
