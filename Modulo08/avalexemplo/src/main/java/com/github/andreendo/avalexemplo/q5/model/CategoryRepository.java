package com.github.andreendo.avalexemplo.q5.model;

import com.github.andreendo.avalexemplo.q5.controller.CategoryForm;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {

    private Logger log = LoggerFactory.getLogger(CategoryRepository.class);

    private final ICategoryDAO categoryDAO;

    public CategoryRepository(ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public void save(CategoryForm categoryForm) {
        Category category = new Category();
        if (categoryForm.getId() != null)
            category.setId(categoryForm.getId());
        category.setName(categoryForm.getName());
        category.setPriority(categoryForm.getPriority());
        category.setTopics(categoryForm.getTopics());
        categoryDAO.save(category);
    }

    public List<CategoryForm> findAll() {
        return categoryDAO.findAll()
                .stream()
                .map(this::toCategoryForm)
                .toList();
    }

    public void delete(long id) {
        categoryDAO.findById(id).ifPresentOrElse(
                category -> categoryDAO.deleteById(id),
                () -> { throw new EntityNotFoundException("Category with id " + id + " not found"); }
        );
    }

    public CategoryForm getById(long id) {
        return categoryDAO.findById(id)
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
