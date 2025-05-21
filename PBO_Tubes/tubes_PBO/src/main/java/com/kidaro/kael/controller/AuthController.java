package com.kidaro.kael.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kidaro.kael.model.Role;
import com.kidaro.kael.model.User;
import com.kidaro.kael.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepo;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setRole(Role.USER); // default role
        return userRepo.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return userRepo.findByUsername(user.getUsername())
            .filter(u -> u.getPassword().equals(user.getPassword()))
            .map(u -> ResponseEntity.ok(u))
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}

