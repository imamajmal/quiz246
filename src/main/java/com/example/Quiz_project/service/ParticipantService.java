package com.example.Quiz_project.service;

import com.example.Quiz_project.dto.*;
import com.example.Quiz_project.entity.Attempt;
import java.util.List;

public interface ParticipantService {
    List<QuizViewDto> listQuizzes();                 // list all quizzes (no answers)
    QuizViewDto getQuizForTaking(Long quizId);       // single quiz (no answers)
    StartAttemptResponse startAttempt(Long quizId, String username);
    SubmitResult submitAttempt(SubmitRequest request, String username);
    Attempt getAttempt(Long attemptId);              // optional for review
}

