package com.github.userrest.controllers;

import com.github.userrest.domain.dtos.ItemListForm;
import com.github.userrest.domain.entities.Item;
import com.github.userrest.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ListItemController {

    private final static Logger logger = LoggerFactory.getLogger(ListItemController.class);

    private final ItemService itemService;

    public ListItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemListForm>> list(Authentication authentication) {
        logger.info("GET /api/item User: {}", authentication.getName());
        var items = itemService.listItems(authentication.getName(), authentication.getAuthorities());

        return ResponseEntity.ok(items);
    }
}
