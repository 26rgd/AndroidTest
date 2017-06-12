package com.powercn.grentechtaxi.common.websocket;

import com.powercn.grentechtaxi.common.unit.StringUnit;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import lombok.Setter;


/**
 * Created by Administrator on 2017/4/27.
 */
@Setter
public class WebSocketTask extends WebSocketClient {
    private String tag=this.getClass().getName();
  private onReceiver onReceiver;

    public WebSocketTask(URI serverUri , Draft draft ) {
        super( serverUri, draft );
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        StringUnit.println( tag,"开流--opened connection" );

    }

    @Override
    public void onMessage(String s) {
        StringUnit.println(tag, "开流--opened onMessage************" );
        StringUnit.println(tag,s);
        if(onReceiver!=null)
        onReceiver.onReceive(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        StringUnit.println( tag,"关流--Connection closed by "  );
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    public interface onReceiver
    {
        public void onReceive(String msg);
    }
}
