package com.github.andreendo.avalexemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Controller
public class Q2Controller {

    private static Logger logger = LoggerFactory.getLogger(Q2Controller.class);

    @GetMapping("/q2")
    public String q2(@RequestParam(name = "all") int pAll, Model model) {
        logger.debug("/q2 pAll: {}", pAll);

        if (pAll == 1) {
            var strs = Arrays.asList("Nome 1", "Nome 2", "Nome 3", "Nome 4", "Nome 5", "Nome 6",  "Nome 7", "Nome 8", "Nome 9", "Nome 10");
            Collections.shuffle(strs);
            model.addAttribute("lista", strs);
            return "q2";
        }

        if (pAll == 2) {
            model.addAttribute("lista", new ArrayList<>());
            return "q2";
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid value for parameter all");
    }
}
