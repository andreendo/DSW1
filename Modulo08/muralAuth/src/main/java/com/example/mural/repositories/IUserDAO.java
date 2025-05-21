package com.example.mural.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserDAO extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User save(User user);
}
