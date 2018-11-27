package com.feng.websocket.web_socket;

import lombok.Data;

@Data
public class UserInfo {
    private String sex = "男";
    private String username;

    public static UserInfo getInstance(){
        return new UserInfo();
    }
}
