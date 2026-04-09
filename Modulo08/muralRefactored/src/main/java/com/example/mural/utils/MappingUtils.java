package com.example.mural.utils;

import com.example.mural.dto.MessageFormDTO;
import com.example.mural.repositories.Message;

public class MappingUtils {

    public static Message mapMessageFormDTOToMessage(MessageFormDTO messageFormDTO) {
        var message = new Message();
        message.setFrom(messageFormDTO.getFrom());
        message.setTo(messageFormDTO.getTo());
        message.setMessage(messageFormDTO.getMessage());
        return message;
    }
}
