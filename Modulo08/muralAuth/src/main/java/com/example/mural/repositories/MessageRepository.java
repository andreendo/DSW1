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

//    private List<Message> messages;

    private final IMessageDAO messageDAO;

    public MessageRepository(IMessageDAO messageDAO) {
//        messages = new ArrayList<>();
        this.messageDAO = messageDAO;
        logger.info("MessageRepository instantiated");
    }

    public void save(Message message) {
//        message.setId(messages.size() + 1);
        message.setTimestamp((new Date()).toString());
        var saved = messageDAO.save(message);
        logger.info("saving message: {}", saved);
//        messages.add(message);
//        logger.info("# messages: {}", messages.size());
    }

    public List<Message> getMessages() {
        return messageDAO.findAllOrderedByIdDesc();
//        return messageDAO.findAll();
//        return messages;
    }
}
