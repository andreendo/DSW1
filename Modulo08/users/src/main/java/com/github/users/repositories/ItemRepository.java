package com.github.users.repositories;

import com.github.users.domain.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {

    List<Item> findByUserUsername(String username);

}
