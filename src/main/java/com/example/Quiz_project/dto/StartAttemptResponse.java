package com.example.Quiz_project.dto;

import java.time.Instant;

public record StartAttemptResponse(Long attemptId, Long quizId, Instant startedAt, Integer timeLimitMinutes) {}
