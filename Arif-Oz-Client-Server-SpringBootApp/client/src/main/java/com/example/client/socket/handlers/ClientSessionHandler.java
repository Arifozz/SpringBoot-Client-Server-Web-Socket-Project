package com.example.client.socket.handlers;

import com.example.client.socket.SocketTopics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

public class ClientSessionHandler implements StompSessionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ClientSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        LOGGER.info("Connected to Websocket");
        subscribeToTopics(session);
    }

    private void subscribeToTopics(StompSession session) {
        LOGGER.info("Subscribing to Topics");
        session.subscribe(SocketTopics.HEART_BEAT.getTopic(), new HeartBeatHandler());
        session.subscribe(SocketTopics.NOTIFICATION.getTopic(), new NotificationHandler());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        LOGGER.error(exception.getMessage());
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        LOGGER.error(exception.getMessage());
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Object.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        LOGGER.info("Received an Object: {}", payload);
    }

}
