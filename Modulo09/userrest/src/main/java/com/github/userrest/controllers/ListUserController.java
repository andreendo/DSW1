package com.github.userrest.controllers;

import com.github.userrest.domain.dtos.ItemListForm;
import com.github.userrest.domain.dtos.UserListForm;
import com.github.userrest.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ListUserController {

    private final static Logger logger = LoggerFactory.getLogger(ListUserController.class);

    private final UserService userService;

    public ListUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserListForm>> listAll() {
        logger.info("GET /user");

        var users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }
}
