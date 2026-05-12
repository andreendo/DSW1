package com.github.userrest.controllers;

import com.github.userrest.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
public class DeleteItemController {

    private final static Logger log = LoggerFactory.getLogger(DeleteItemController.class);

    private final ItemService itemService;

    public DeleteItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        log.info("DELETE /api/item with id {}", id);

        itemService.deleteItem(id, authentication.getName(), authentication.getAuthorities());

        return ResponseEntity.noContent().build();
    }
}
