package com.github.andreendo.avalexemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Controller
@RequestMapping("/q4")
public class Q4Controller {

    private static Logger logger = LoggerFactory.getLogger(Q4Controller.class);

    @GetMapping
    public String q2() {
        logger.info("GET /q4");
        return "q4";
    }

    @PostMapping
    public String postQ2() {
        logger.info("POST /q4");
        return "q4";
    }
}
