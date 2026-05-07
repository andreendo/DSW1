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
@RequestMapping("/admin")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private ItemService itemService;

    public AdminController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/main")
    public String main(Authentication authentication, Model model) {
        var currentUser = authentication.getName();
        logger.info("GET admin/main user: {}", currentUser);

        // Improvement: show edit/delete options only for items with permission
        model.addAttribute("items", itemService.findAll());

        return "admin/main";
    }
}
