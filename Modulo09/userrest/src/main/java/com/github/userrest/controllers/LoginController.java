package com.github.userrest.controllers;

import com.github.userrest.domain.dtos.LoginRequestForm;
import com.github.userrest.services.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Make it login before access web service")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestForm loginRequestForm) {
        log.info("Login request received : {}", loginRequestForm);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestForm.username(), loginRequestForm.password()));
        return ResponseEntity.ok(jwtService.generateToken(loginRequestForm.username()));
    }

}
