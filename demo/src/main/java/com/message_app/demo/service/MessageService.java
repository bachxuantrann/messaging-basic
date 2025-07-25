package com.message_app.demo.service;

import com.message_app.demo.entity.Message;
import com.message_app.demo.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    public Message saveMessage(String content) {
        Message message = new Message();
        message.setContent(content);
        return messageRepository.save(message);
    }
}
