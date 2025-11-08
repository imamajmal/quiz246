package com.example.Quiz_project.controller;

import com.example.Quiz_project.dto.*;
import com.example.Quiz_project.entity.Attempt;
import com.example.Quiz_project.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService service;

    // browse quizzes
    @GetMapping("/quizzes")
    public List<QuizViewDto> listQuizzes() {
        return service.listQuizzes();
    }

    // view single quiz (no answers)
    @GetMapping("/quizzes/{quizId}")
    public QuizViewDto getQuiz(@PathVariable Long quizId) {
        return service.getQuizForTaking(quizId);
    }

    // start attempt (captures username from auth)
    @PostMapping("/quizzes/{quizId}/start")
    public StartAttemptResponse start(@PathVariable Long quizId, Authentication auth) {
        return service.startAttempt(quizId, auth.getName());
    }

    // submit attempt (answers:{questionId, optionId}[])
    @PostMapping("/submit")
    public SubmitResult submit(@RequestBody SubmitRequest req, Authentication auth) {
        return service.submitAttempt(req, auth.getName());
    }

    // optional: view attempt summary
    @GetMapping("/attempts/{attemptId}")
    public Attempt getAttempt(@PathVariable Long attemptId) {
        return service.getAttempt(attemptId);
    }
}

