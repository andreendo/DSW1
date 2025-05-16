package com.example.mural.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MessageRepository {

    private Logger logger = LoggerFactory.getLogger(MessageRepository.class);

    private List<Message> messages;

    public MessageRepository() {
        messages = new ArrayList<>();
        logger.info("MessageRepository instantiated");
    }

    public void save(Message message) {
        message.setId(messages.size() + 1);
        message.setTimestamp((new Date()).toString());
        logger.info("saving message: {}", message);
        messages.add(message);
        logger.info("# messages: {}", messages.size());
    }

    public List<Message> getMessages() {
        return messages;
    }
}
