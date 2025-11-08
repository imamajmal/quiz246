package com.example.Quiz_project.dto;

import java.util.List;

public record QuizViewDto(Long id, String title, String description, Integer timeLimit,
                          List<QuestionDto> questions) {
    public record QuestionDto(Long id, String text, List<OptionDto> options) {}
    public record OptionDto(Long id, String text) {}
}

