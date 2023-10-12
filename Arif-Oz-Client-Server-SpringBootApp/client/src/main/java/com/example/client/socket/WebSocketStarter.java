package com.example.client.socket;

import com.example.client.socket.handlers.ClientSessionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class WebSocketStarter {

    private static final String URL_FORMAT = "%s://%s:%s/%s";

    @Value("${websocket.protocol}")
    private String protocol;

    @Value("${websocket.host}")
    private String host;

    @Value("${websocket.port}")
    private String port;

    @Value("${websocket.endpoint}")
    private String endpoint;

    public static final AtomicReference<LocalDateTime> LAST_HEART_BEAT = new AtomicReference<>(LocalDateTime.now());

    @EventListener
    public void applicationAvailable(AvailabilityChangeEvent<ReadinessState> event) {
        if (event.getState().equals(ReadinessState.ACCEPTING_TRAFFIC)) {
            connectToWebSocket();
        }
    }

    private void connectToWebSocket() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        List<MessageConverter> converters = new ArrayList<>();
        converters.add(new MappingJackson2MessageConverter());
        converters.add(new StringMessageConverter());
        stompClient.setMessageConverter(new CompositeMessageConverter(converters));
        StompSessionHandler sessionHandler = new ClientSessionHandler();
        String url = String.format(URL_FORMAT, protocol, host, port, endpoint);
        stompClient.connectAsync(url, sessionHandler);
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    private void checkConnection() {
        if (LAST_HEART_BEAT.get().isBefore(LocalDateTime.now().minusSeconds(10))) {
            connectToWebSocket();
        }
    }

}
