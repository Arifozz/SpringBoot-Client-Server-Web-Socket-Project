package com.example.client.socket.handlers;

import com.example.client.socket.WebSocketStarter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class HeartBeatHandler implements StompFrameHandler {

    public HeartBeatHandler() {
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        WebSocketStarter.LAST_HEART_BEAT.set(LocalDateTime.now());
    }

}
