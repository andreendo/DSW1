package com.github.userrest.controllers;

import com.github.userrest.domain.dtos.ItemAddForm;
import com.github.userrest.domain.dtos.ItemListForm;
import com.github.userrest.services.ItemService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
public class SaveItemController {

    private final static Logger log = LoggerFactory.getLogger(SaveItemController.class);

    private final ItemService itemService;

    public SaveItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemListForm> save(@RequestBody @Valid ItemAddForm itemAddForm, Authentication authentication) {
        return ResponseEntity.ok(
                itemService.save(itemAddForm, authentication.getName())
        );
    }
}
