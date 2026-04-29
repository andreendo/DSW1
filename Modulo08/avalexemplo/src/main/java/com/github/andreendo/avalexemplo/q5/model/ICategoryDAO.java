package com.github.andreendo.avalexemplo.q5.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICategoryDAO extends CrudRepository<Category, Long> {

    List<Category> findAll();

}
