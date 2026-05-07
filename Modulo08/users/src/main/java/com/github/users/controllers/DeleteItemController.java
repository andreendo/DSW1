package com.github.users.controllers;

import com.github.users.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/items")
public class DeleteItemController {

    private static final Logger logger = LoggerFactory.getLogger(DeleteItemController.class);
    private final ItemService itemService;

    public DeleteItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        var userRole = authentication.getAuthorities().iterator().next();
        logger.info("Delete item id: {} (Role: {})", id, userRole);

        itemService.deleteItem(id, authentication.getName(), authentication.getAuthorities());
        redirectAttributes.addFlashAttribute("message", "Successfully deleted item");

        if (userRole.getAuthority().equals("ROLE_ADMIN")) {
            return "redirect:/admin/main";
        }

        return "redirect:/user/main";
    }

}
