// QuizResultEmailRequest.java
package com.example.Quiz_project.dto;
public record QuizResultEmailRequest(String to, String name, String quizTitle, Integer score, Integer total) {}

