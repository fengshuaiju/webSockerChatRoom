package com.feng.websocket.web_socket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SocketInfo implements Serializable {

    private String username;
    private String message;
    private MessageType messageType;
    private Integer onlineNumber;

    public SocketInfo(){
    }

    public SocketInfo(String username, String message, MessageType messageType) {
        this.username = username;
        this.message = message;
        this.messageType = messageType;
    }

    enum MessageType {
        JOIN,
        MESSAGE
    }
}
