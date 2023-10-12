package com.example.client.socket;

public enum SocketTopics {

    HEART_BEAT("/heart-beat"),
    NOTIFICATION("/notification");

    private final String topic;
    private static final String WEB_SOCKET_TOPIC_PREFIX = "/topic";

    SocketTopics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return WEB_SOCKET_TOPIC_PREFIX + topic;
    }

}
