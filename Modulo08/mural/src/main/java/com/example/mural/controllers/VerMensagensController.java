package com.example.mural.controllers;

import com.example.mural.dto.ListedMessage;
import com.example.mural.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class VerMensagensController {

    private final Logger logger = LoggerFactory.getLogger(VerMensagensController.class);

    private MessageRepository messageRepository;

    public VerMensagensController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/mensagens")
    public String verMensagens(Model model) {
        logger.info("GET /mensagens");

        var listedMessages = new ArrayList<ListedMessage>();
        messageRepository.getMessages().forEach(m -> {
            listedMessages.add(new ListedMessage(
                    m.getFrom(),
                    m.getTo(),
                    m.getMessage(),
                    m.getTimestamp()
            ));
        });

        model.addAttribute("messages", listedMessages);
        logger.info("Listed messages: {}", listedMessages);

        return "mensagens";
    }

}
