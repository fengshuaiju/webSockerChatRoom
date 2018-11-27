package com.feng.websocket.controller;

import com.feng.websocket.web_socket.MyWebSocket;
import com.feng.websocket.web_socket.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class WebSocketController {

    @GetMapping("/socket/userinfo")
    public Set<UserInfo> userinfo() {
        Set<UserInfo> userInfos = MyWebSocket.userInfo();
        return userInfos;
    }

}
