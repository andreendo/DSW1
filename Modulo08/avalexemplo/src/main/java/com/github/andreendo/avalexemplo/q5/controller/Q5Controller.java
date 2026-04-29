package com.github.andreendo.avalexemplo.q5.controller;

import com.github.andreendo.avalexemplo.q5.model.CategoryRepository;
import com.github.andreendo.avalexemplo.q5.model.PriorityRepository;
import com.github.andreendo.avalexemplo.q5.model.TopicRepository;
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

    private final CategoryRepository categoryRepository;
    private final TopicRepository topicRepository;
    private final PriorityRepository priorityRepository;

    public Q5Controller(CategoryRepository categoryRepository, TopicRepository topicRepository, PriorityRepository priorityRepository) {
        this.categoryRepository = categoryRepository;
        this.topicRepository = topicRepository;
        this.priorityRepository = priorityRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        log.info("GET /q5/list");

        model.addAttribute("categories", categoryRepository.findAll());

        return "q5/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        log.info("GET /q5/add");

        model.addAttribute("priorities", priorityRepository.getPriorities());
        model.addAttribute("topics", topicRepository.getTopics());
        model.addAttribute("categoryForm", new CategoryForm());

        return "q5/form";
    }

    @PostMapping("/save")
    public String addPost(@ModelAttribute CategoryForm categoryForm, Model model, RedirectAttributes redirectAttributes) {
        log.info("POST /q5/save {}", categoryForm);

        categoryRepository.save(categoryForm);
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
        categoryRepository.delete(id);

        return "redirect:/q5/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id", required = true) long id, Model model) {
        log.info("GET /q5/edit?id={}", id);

        model.addAttribute("priorities", priorityRepository.getPriorities());
        model.addAttribute("topics", topicRepository.getTopics());
        var categoryForm = categoryRepository.getById(id);
        log.info("GET /q5/edit?id={} {}", id, categoryForm);
        model.addAttribute("categoryForm", categoryForm);

        return "q5/form";
    }
}
