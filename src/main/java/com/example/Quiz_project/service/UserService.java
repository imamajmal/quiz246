package com.example.Quiz_project.service;

import com.example.Quiz_project.entity.User;

public interface UserService {
    User register(User user);
    String login(String username, String password);
    User getByUsername(String username);
}

