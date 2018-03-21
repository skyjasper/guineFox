package com.guinea.task;

import com.guinea.communications.netty.webSocket.Global;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @title
 * @author: shiky
 * @describe:
 * @date: 2016/7/18
 */
public class RemindMessageTask implements Runnable{

    @Override
    public void run() {
        TextWebSocketFrame tws = new TextWebSocketFrame("00|卡卡西|00");
        //群发
        Global.group.writeAndFlush(tws);
    }
}
