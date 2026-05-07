package com.github.users.controllers;

import com.github.users.domain.dtos.ItemAddForm;
import com.github.users.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/item")
public class SaveItemController {

    private final ItemService itemService;

    public SaveItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/add")
    public String addItem(Model model) {
        model.addAttribute("item", new ItemAddForm());
        return "item/form";
    }

    @PostMapping("/save")
    public String saveItem(@Valid @ModelAttribute("item") ItemAddForm itemAddForm, BindingResult bindingResult, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "item/form";
        }

        itemService.saveItem(itemAddForm, authentication.getName());
        redirectAttributes.addFlashAttribute("message", "Item saved successfully");

        if (authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
        ) {
            return "redirect:/admin/main";
        }
        return "redirect:/user/main";
    }
}
