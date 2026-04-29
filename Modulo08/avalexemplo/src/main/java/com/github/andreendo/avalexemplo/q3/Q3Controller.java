package com.github.andreendo.avalexemplo.q3;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class Q3Controller {

    private Logger logger = LoggerFactory.getLogger(Q3Controller.class);

    @GetMapping("/q3")
    public String q3(Model model) {
        model.addAttribute("pessoaForm", new PessoaForm());
        return "q3";
    }

    @PostMapping("/q3criarsessao")
    public String q3criarsessao(@ModelAttribute PessoaForm pessoaForm, HttpSession session) {
        logger.info("POST /q3criarsessao: {}", pessoaForm);

        session.setAttribute("pessoaForm", pessoaForm);

        return "redirect:/";
    }

    @GetMapping("/q3mostrarsessao")
    public String mostrarSessao(HttpSession session, Model model) {
        model.addAttribute("sessionId", session.getId());

        long lastAccessTime = session.getLastAccessedTime();
        Date date = new Date(lastAccessTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        model.addAttribute("lastAccessDate", formattedDate);
        model.addAttribute("pessoaForm", session.getAttribute("pessoaForm"));

        return "q3_mostrarsessao";
    }
}
