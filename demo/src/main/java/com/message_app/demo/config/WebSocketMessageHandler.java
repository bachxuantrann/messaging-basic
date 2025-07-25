package com.message_app.demo.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketMessageHandler extends TextWebSocketHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final List<WebSocketSession> webSocketSessions = new CopyOnWriteArrayList<>();

    public WebSocketMessageHandler(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        kafkaTemplate.send("message-topic", message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
    }

    public static List<WebSocketSession> getWebSocketSessions() {
        return webSocketSessions;
    }
}
