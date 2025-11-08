package com.example.Quiz_project.controller;

import com.example.Quiz_project.dto.*;
import com.example.Quiz_project.entity.Attempt;
import com.example.Quiz_project.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService service;

    // start attempt
    @PostMapping("/quizzes/{quizId}/start")
    public StartAttemptResponse start(@PathVariable Long quizId, Authentication auth) {
        return service.startAttempt(quizId, auth.getName());
    }

    // submit answers
    @PostMapping("/submit")
    public SubmitResult submit(@RequestBody SubmitRequest req, Authentication auth) {
        return service.submit(req, auth.getName());
    }

    // view attempt (summary)
    @GetMapping("/attempts/{attemptId}")
    public Attempt getAttempt(@PathVariable Long attemptId) {
        return service.getAttempt(attemptId);
    }
}

