package com.example.backend.service;

import com.example.backend.dto.AuthRequest;
import com.example.backend.dto.EditRequest;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public ResponseEntity<Map<String,String>> registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("response,","Username already exists"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        //return ResponseEntity.ok("User registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("response","User registered"));
    }

    public ResponseEntity<Map<String,String>> loginUser(AuthRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            if (passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("response","Login successful"));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("response","Invalid credentials"));
    }

    public ResponseEntity<Map<String,String>> editUser(EditRequest request) {
        Optional<User> oldUserOpt = userRepository.findByUsername(request.oldUsername());
        if (oldUserOpt.isEmpty()) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (userRepository.existsByUsernameAndDateDeletedIsNull(request.newUsername())) {
            //return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        User oldUser = oldUserOpt.get();
        oldUser.setDateDeleted(LocalDateTime.now());
        userRepository.save(oldUser);

        User newUser = new User();
        newUser.setUsername(request.newUsername());
        newUser.setPassword(passwordEncoder.encode(request.newPassword()));
        newUser.setDateCreated(LocalDateTime.now());
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("response","User Updated"));

    }
}
