package com.example.backend.controller;


import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/active")
    public List<User> getAllActiveUsers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getDateDeleted() == null)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/username")
    public Optional<User> getUserName(@PathVariable Long userId) {
        //Long id = userid

        return userRepository.findByUserId(userId);//.stream()
               // .filter(user -> user.getDateDeleted() == null);
                //.collect(Collectors.);
    }

}

