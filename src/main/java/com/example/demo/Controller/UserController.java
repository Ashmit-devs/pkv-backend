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



@RestController
@RequestMapping("/api/auth")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Username is already taken!";
        }
        userRepository.save(user);
        return "User registered successfully!";
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
