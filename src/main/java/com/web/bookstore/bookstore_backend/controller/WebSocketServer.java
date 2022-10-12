package com.web.bookstore.bookstore_backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bookstore.bookstore_backend.Msg;
import com.web.bookstore.bookstore_backend.MsgUtil;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/orderFinish/{userID}")
@Component
public class WebSocketServer {
    public WebSocketServer() {
        //每当有一个连接，都会执行一次构造方法
        System.out.println("new connection");
    }

//    private static final AtomicInteger COUNT = new AtomicInteger();

    private static final ConcurrentHashMap<Integer, Session> sessions = new ConcurrentHashMap<>();

    public void sendMessage(Session toSession, Msg msg) {
        if (toSession != null) {
            try {
                String json = new ObjectMapper().writeValueAsString(msg);
                toSession.getBasicRemote().sendText(json);
            } catch ( IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("not open session");
        }
    }

    public void sendMessageToUser(Integer userID, boolean success, String message) {
        System.out.println(userID);
        Session toSession = sessions.get(userID);
        sendMessage(toSession, MsgUtil.makeMsg(success,message));
        System.out.println(message);
    }


//    @OnMessage
//    public void onMessage(String message) {
//        System.out.println("服务器收到消息：" + message);
//    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userID") Integer userID) {
        if (sessions.get(userID) != null) {
            return;
        }
        sessions.put(userID, session);
        System.out.println(userID + "open" );

    }

    @OnClose
    public void onClose(@PathParam("userID") Integer userID) {
        sessions.remove(userID);
        System.out.println(userID + "close" );
    }

//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        System.out.println("发生错误");
//        throwable.printStackTrace();
//    }
}
