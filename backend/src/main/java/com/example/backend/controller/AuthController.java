package com.example.backend.controller;

import com.example.backend.dto.AuthRequest;
import com.example.backend.dto.EditRequest;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody User user) {
        return userService.registerUser(user);
    }



//    @PostMapping("/api/auth/register")
//    public ResponseEntity<String> register(@RequestBody User user) {
//        if (userRepository.findByUsername(user.getUsername())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
//        }
//
//        user.setDateCreated(LocalDateTime.now());
//        userRepository.save(user);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body("User registered");
//    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody AuthRequest request) {
        return userService.loginUser(request);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        user.setDateDeleted(LocalDateTime.now());
        userRepository.save(user);

        //taskRepository.clearUserIdForDeletedUser(user.getId()); // if needed later

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/edituser")
    public ResponseEntity<Map<String,String>> editUser(@RequestBody EditRequest request) {

        return userService.editUser(request);

        // TODO: Migrate tasks from oldUser.getId() → newUser.getId()

    }



}
