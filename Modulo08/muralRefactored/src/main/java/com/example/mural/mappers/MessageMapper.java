package com.example.mural.mappers;

import com.example.mural.dto.MessageFormDTO;
import com.example.mural.repositories.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message toEntity(MessageFormDTO messageFormDTO) {
        Message message = new Message();
        message.setId(messageFormDTO.getId());
        message.setFrom(messageFormDTO.getFrom());
        message.setTo(messageFormDTO.getTo());
        message.setMessage(messageFormDTO.getMessage());
        return message;
    }

    public MessageFormDTO toDTO(Message message) {
        var messageFormDTO = new MessageFormDTO();
        messageFormDTO.setId(message.getId());
        messageFormDTO.setFrom(message.getFrom());
        messageFormDTO.setTo(message.getTo());
        messageFormDTO.setMessage(message.getMessage());

        return messageFormDTO;
    }

}
