package com.github.andreendo.avalexemplo.q5.controllers;

import com.github.andreendo.avalexemplo.q5.domain.dtos.CategoryForm;
import com.github.andreendo.avalexemplo.q5.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/q5")
public class Q5Controller {

    private static final Logger log = LoggerFactory.getLogger(Q5Controller.class);

    private final CategoryService categoryService;

    public Q5Controller(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        log.info("GET /q5/list");

        model.addAttribute("categories", categoryService.getAllCategories());

        return "q5/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        log.info("GET /q5/add");

        model.addAttribute("priorities", categoryService.getAllPriorities());
        model.addAttribute("topics", categoryService.getAllTopics());
        model.addAttribute("categoryForm", new CategoryForm());

        return "q5/form";
    }

    @PostMapping("/save")
    public String addPost(@ModelAttribute CategoryForm categoryForm, Model model, RedirectAttributes redirectAttributes) {
        log.info("POST /q5/save {}", categoryForm);

        categoryService.save(categoryForm);
        if (categoryForm.getId() != null) {
            redirectAttributes.addFlashAttribute("message", "Category updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Category added successfully!");
        }

        return "redirect:/q5/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id", required = true) long id, RedirectAttributes redirectAttributes) {
        log.info("GET /q5/delete?id={}", id);

        redirectAttributes.addFlashAttribute("message", "Category removed!");
        categoryService.delete(id);

        return "redirect:/q5/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id", required = true) long id, Model model) {
        log.info("GET /q5/edit?id={}", id);

        model.addAttribute("priorities", categoryService.getAllPriorities());
        model.addAttribute("topics", categoryService.getAllTopics());
        var categoryForm = categoryService.getById(id);
        log.info("GET /q5/edit?id={} {}", id, categoryForm);
        model.addAttribute("categoryForm", categoryForm);

        return "q5/form";
    }
}
