package com.example.client.socket.handlers;

import com.example.client.model.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import java.lang.reflect.Type;

public class NotificationHandler implements StompFrameHandler {

    private static final Logger LOGGER = LogManager.getLogger(NotificationHandler.class);

    public NotificationHandler() {
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Notification.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Notification notification = (Notification) payload;
        LOGGER.info(notification.toString());
    }

}
