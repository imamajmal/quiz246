package com.example.Quiz_project.service;

import com.example.Quiz_project.dto.StartAttemptResponse;
import com.example.Quiz_project.dto.SubmitRequest;
import com.example.Quiz_project.dto.SubmitResult;
import com.example.Quiz_project.entity.Attempt;

public interface SubmissionService {
    StartAttemptResponse startAttempt(Long quizId, String username);
    SubmitResult submit(SubmitRequest request, String username);
    Attempt getAttempt(Long attemptId);
}

