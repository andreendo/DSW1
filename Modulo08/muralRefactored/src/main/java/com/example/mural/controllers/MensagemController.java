package com.example.mural.controllers;

import com.example.mural.dto.ListedMessage;
import com.example.mural.dto.MessageDTO;
import com.example.mural.dto.MessageFormDTO;
import com.example.mural.repositories.Message;
import com.example.mural.repositories.MessageRepository;
import com.example.mural.utils.MappingUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/mensagem")
public class MensagemController {

    private static final Logger log = LoggerFactory.getLogger(MensagemController.class);
    private MessageRepository messageRepository;

    public MensagemController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        var messages = new ArrayList<MessageDTO>();
        messageRepository.getMessages().forEach(m -> {
            messages.add(new MessageDTO(
                    m.getId(),
                    m.getFrom(),
                    m.getTo(),
                    m.getMessage(),
                    m.getTimestamp()
            ));
        });

        model.addAttribute("messages", messages);
        log.info("Listed messages: {}", messages);

        return "mensagem/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attr) {
        log.info("Delete message: {}", id);
        messageRepository.delete(id);
        attr.addFlashAttribute("success", "message " + id + " deleted");
        return "redirect:/mensagem/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        log.info("Create message");
        model.addAttribute("message", new MessageFormDTO());
        return "mensagem/form";
    }

    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("message") MessageFormDTO messageFormDTO, BindingResult result, RedirectAttributes attr) {
        log.info("Create message POST {}", messageFormDTO);
        if (!result.hasErrors() && messageFormDTO.getFrom().equals(messageFormDTO.getTo())) {
            result.rejectValue("from", "error.message", "From and to are the same");
            result.rejectValue("to", "error.message", "From and to are the same");
        }

        if (result.hasErrors()) {
            return "mensagem/form";
        }
        Message message = MappingUtils.mapMessageFormDTOToMessage(messageFormDTO);
        messageRepository.save(message);

        attr.addFlashAttribute("success", "message created");
        return "redirect:/mensagem/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        log.info("Edit message {}", id);
        var messageDTO = new MessageFormDTO();
        messageDTO.setId(id);
        model.addAttribute("message", messageDTO);
        return "mensagem/form";
    }
}
