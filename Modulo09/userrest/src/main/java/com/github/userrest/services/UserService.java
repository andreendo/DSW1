package com.github.userrest.services;

import com.github.userrest.domain.dtos.UserListForm;
import com.github.userrest.domain.entities.User;
import com.github.userrest.repositories.UserRepository;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        logger.info("user: {}, password: {}", user.getUsername(), encodedPassword);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public long count() {
        return userRepository.count();
    }

    public List<UserListForm> listAllUsers() {
        List<UserListForm> userListForms = new ArrayList<>();
        userRepository.findAll().forEach(u -> {
            UserListForm userListForm = new UserListForm();
            userListForm.setId(u.getId());
            userListForm.setUsername(u.getUsername());
            userListForm.setRole(u.getRole());
            userListForms.add(userListForm);
        });
        return userListForms;
    }
}
