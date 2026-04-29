package com.github.andreendo.avalexemplo.q5.services;

import com.github.andreendo.avalexemplo.q5.domain.entities.Category;
import com.github.andreendo.avalexemplo.q5.domain.dtos.CategoryForm;
import com.github.andreendo.avalexemplo.q5.repositories.CategoryRepository;
import com.github.andreendo.avalexemplo.q5.repositories.PriorityRepository;
import com.github.andreendo.avalexemplo.q5.repositories.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TopicRepository topicRepository;
    private final PriorityRepository priorityRepository;

    public CategoryService(CategoryRepository categoryRepository, TopicRepository topicRepository, PriorityRepository priorityRepository) {
        this.categoryRepository = categoryRepository;
        this.topicRepository = topicRepository;
        this.priorityRepository = priorityRepository;
    }

    public List<String> getAllTopics() {
        return topicRepository.getTopics();
    }

    public List<String> getAllPriorities() {
        return priorityRepository.getPriorities();
    }

    public List<CategoryForm> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toCategoryForm)
                .toList();
    }

    public void save(CategoryForm categoryForm) {
        Category category = new Category();
        if (categoryForm.getId() != null)
            category.setId(categoryForm.getId());
        category.setName(categoryForm.getName());
        category.setPriority(categoryForm.getPriority());
        category.setTopics(categoryForm.getTopics());
        categoryRepository.save(category);
    }

    public void delete(long id) {
        categoryRepository.findById(id).ifPresentOrElse(
                category -> categoryRepository.deleteById(id),
                () -> { throw new EntityNotFoundException("Category with id " + id + " not found"); }
        );
    }

    public CategoryForm getById(long id) {
        return categoryRepository.findById(id)
                .map(this::toCategoryForm)
                .orElseThrow(EntityNotFoundException::new);
    }

    public CategoryForm toCategoryForm(Category category) {
        CategoryForm categoryForm = new CategoryForm();

        categoryForm.setId(category.getId());
        categoryForm.setName(category.getName());
        categoryForm.setPriority(category.getPriority());
        categoryForm.setTopics(category.getTopics());

        return categoryForm;
    }
}
