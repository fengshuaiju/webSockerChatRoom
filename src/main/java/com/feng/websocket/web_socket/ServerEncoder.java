package com.feng.websocket.web_socket;


import com.alibaba.fastjson.JSONObject;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ServerEncoder implements Encoder.Text<SocketInfo> {

    @Override
    public String encode(SocketInfo message) {
        return JSONObject.toJSONString(message);
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("MessageEncoder - init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageEncoder - destroy method called");
    }

}
