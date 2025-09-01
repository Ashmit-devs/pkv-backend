package com.example.demo.Controller;


import java.util.HashMap;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        if (userRepository.existsByEmail(user.getEmail())) {
            response.put("message", "Error: Email is already taken!");
            return response;
        }
        User savedUser = userRepository.save(user);
        response.put("message", "User registered successfully!");
        response.put("userId", savedUser.getId());
        return response;
    }

    @PostMapping("/login")
    public Object loginUser(@RequestBody User loginRequest) {
    Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
    if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
        // Build a JSON-like response using a Map
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful!");
        response.put("userId", user.get().getId()); // assuming User has getId()
        return response;
    } else {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Invalid email or password!");
        return response;
    }
}
}
