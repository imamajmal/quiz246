package com.example.Quiz_project.service;

public interface JwtService {
    String generateToken(String username, String role);
    String extractUsername(String token);
    boolean validateToken(String token);
}

