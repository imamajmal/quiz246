package com.example.Quiz_project.dto;

import java.util.List;

public record SubmitRequest(Long attemptId, java.util.List<Answer> answers) {
    public record Answer(Long questionId, Long optionId) {}
}

