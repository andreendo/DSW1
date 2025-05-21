package com.example.mural.repositories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final IUserDAO userDAO;

    public UserRepository(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public long count() {
        return userDAO.count();
    }

    public void save(String username, String password, String role) {
        var encoder = new BCryptPasswordEncoder();
        var user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRole(role);
        userDAO.save(user);
    }
}
