package com.github.andreendo.avalexemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Q1Controller {

    private static Logger logger = LoggerFactory.getLogger(Q1Controller.class);

    @GetMapping("/q1")
    public String index(@RequestParam(name = "p", defaultValue = "no") String p) {
        logger.info("p={}", p);

        if ("yes".equals(p)) {
            return "q1_yes";
        } else if ("ok".equals(p)) {
            return "q1_ok";
        } else {
            return "q1_no";
        }
    }
}
