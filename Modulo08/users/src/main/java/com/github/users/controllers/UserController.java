package com.github.users.controllers;

import com.github.users.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    private final ItemService itemService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/main")
    public String main(Authentication authentication, Model model) {
        var currentUser = authentication.getName();
        logger.info("GET user/main, user: {}", currentUser);

        model.addAttribute("items", itemService.findAllByOwner(currentUser));

        return "user/main";
    }
}
