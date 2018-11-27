package com.feng.websocket.web_socket;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ServerDecoder implements Decoder.Text<SocketInfo> {

    @Override
    public SocketInfo decode(String socketInfo) {
        return JSON.parseObject(socketInfo, SocketInfo.class);
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("MessageDecoder -init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageDecoder - destroy method called");
    }

}
