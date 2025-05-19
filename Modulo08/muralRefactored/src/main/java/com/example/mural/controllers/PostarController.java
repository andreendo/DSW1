package com.example.mural.controllers;

import com.example.mural.dto.SendMessageForm;
import com.example.mural.repositories.Message;
import com.example.mural.repositories.MessageRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostarController {

    private static final Logger logger = LoggerFactory.getLogger(PostarController.class);

    private final MessageRepository messageRepository;

    public PostarController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/postar")
    public String get(Model model) {
        logger.info("Get /postar");
        model.addAttribute("sendMessageForm", new SendMessageForm());
        return "postar";
    }

    @PostMapping("/postar")
    public String post(
            @Valid @ModelAttribute SendMessageForm sendMessageForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        logger.info("Post /postar - {}", sendMessageForm);

        if (!sendMessageForm.getFrom().trim().equals("") &&
                sendMessageForm.getFrom().equals(sendMessageForm.getTo())) {
            logger.info("From and to equals");
            bindingResult.reject("FromAndToSame", "From and to are the same");
        }

        if (bindingResult.hasErrors()) {
            return "postar";
        }

        var message = new Message();
        message.setFrom(sendMessageForm.getFrom());
        message.setTo(sendMessageForm.getTo());
        message.setMessage(sendMessageForm.getMessage());
        messageRepository.save(message);

        redirectAttributes.addFlashAttribute("success", "Mensagem enviada com sucesso");
        return "redirect:/mensagens";
    }

}
