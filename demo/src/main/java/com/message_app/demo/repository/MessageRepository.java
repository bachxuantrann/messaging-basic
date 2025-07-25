package com.message_app.demo.repository;

import com.message_app.demo.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message,String> {

}
