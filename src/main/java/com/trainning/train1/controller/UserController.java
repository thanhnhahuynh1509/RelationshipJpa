package com.trainning.train1.controller;

import com.trainning.train1.entity.User;
import com.trainning.train1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {

        User user = userRepository.findByUsername(username);
        return user;
    }

}
