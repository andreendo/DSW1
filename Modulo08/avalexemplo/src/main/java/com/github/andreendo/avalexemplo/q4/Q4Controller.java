package com.github.andreendo.avalexemplo.q4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/q4")
public class Q4Controller {

    private static Logger logger = LoggerFactory.getLogger(Q4Controller.class);

    private FuncionarioDAO funcionarioDAO;

    public Q4Controller(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    @GetMapping
    public String q4(Model model) {
        logger.info("GET /q4");
        List<String> allTecnologias = Arrays.asList("Java", "PHP", "Node.js", "CPP", "Dot.Net");
        model.addAttribute("tecnologias", allTecnologias);
        model.addAttribute("funcionarioForm", new FuncionarioForm());
        return "q4";
    }

    @PostMapping
    public String postQ4(@ModelAttribute FuncionarioForm funcionarioForm, RedirectAttributes redirectAttributes) {
        logger.info("POST /q4 {}", funcionarioForm);

        funcionarioDAO.salvar(funcionarioForm);

        redirectAttributes.addFlashAttribute("sucesso", true);

        return "redirect:/q4";
    }
}
