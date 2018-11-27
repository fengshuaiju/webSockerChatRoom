package com.feng.websocket.web_socket;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Slf4j
@Component
@ServerEndpoint(
        value = "/websocket/{username}",
        encoders = {ServerEncoder.class},
        decoders = {ServerDecoder.class}
)
public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    @Getter
    private UserInfo userInfo = UserInfo.getInstance();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value="username") String username, Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新连接加入！ "+ username +"当前在线人数为" + getOnlineCount());
        this.userInfo.setUsername(username);
        joinRoom(username);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String socketInfo, Session session) {
        //群发消息，所有的聊天窗口都能看到
        sendInfoToAll(JSON.parseObject(socketInfo, SocketInfo.class));
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 向前端发送消息
     */
    public void sendMessage(Session session, String username, String message, SocketInfo.MessageType messageType) {
        try {
            session.getBasicRemote().sendObject(new SocketInfo(username, message, messageType));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入聊天室，向前端所有的聊天窗口发送消息
     */
    public void joinRoom(String username) {
        try {
            webSocketSet.stream().forEach(webSocket -> {
                try {
                    webSocket.session.getBasicRemote().sendObject(new SocketInfo(username, "已经加入聊天室", SocketInfo.MessageType.JOIN, getOnlineCount()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 群发自定义消息
     */
    public void sendInfoToAll(SocketInfo message) {
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(item.session, message.getUsername(), message.getMessage(), SocketInfo.MessageType.MESSAGE);
            } catch (Exception e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }

    public static Set<UserInfo> userInfo() {
        return webSocketSet.stream().map(MyWebSocket::getUserInfo).collect(Collectors.toSet());
    }

}
