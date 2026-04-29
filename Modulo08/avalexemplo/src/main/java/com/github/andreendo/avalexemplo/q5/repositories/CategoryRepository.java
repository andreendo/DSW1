package com.github.andreendo.avalexemplo.q5.repositories;

import com.github.andreendo.avalexemplo.q5.domain.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findAll();

}
