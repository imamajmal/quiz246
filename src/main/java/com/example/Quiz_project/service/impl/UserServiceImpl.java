package com.example.Quiz_project.service.impl;

import com.example.Quiz_project.entity.Role;
import com.example.Quiz_project.entity.User;
import com.example.Quiz_project.repository.UserRepository;
import com.example.Quiz_project.service.JwtService;
import com.example.Quiz_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Override
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
public String login(String username, String password) {
    User user = repo.findByUsername(username)
            .orElse(null);

    if (user == null)
        return "User not found";

    if (!encoder.matches(password, user.getPassword()))
        return "Invalid credentials";

    return jwtService.generateToken(user.getUsername(), user.getRole().name());
}

@Override
public User getByUsername(String username) {
    return repo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
}

}

