package com.github.userrest.repositories;

import com.github.userrest.domain.entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    List<Item> findByUserUsername(String username);

    List<Item> findAll();
}
