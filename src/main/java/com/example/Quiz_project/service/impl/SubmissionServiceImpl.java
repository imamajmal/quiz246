package com.example.Quiz_project.service.impl;

import com.example.Quiz_project.dto.StartAttemptResponse;
import com.example.Quiz_project.dto.SubmitRequest;
import com.example.Quiz_project.dto.SubmitResult;
import com.example.Quiz_project.entity.*;
import com.example.Quiz_project.repository.*;
import com.example.Quiz_project.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;
    private final OptionRepository optionRepo;
    private final AttemptRepository attemptRepo;
    private final AttemptAnswerRepository attemptAnswerRepo;

    @Override
    public StartAttemptResponse startAttempt(Long quizId, String username) {
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Attempt attempt = Attempt.builder()
                .quiz(quiz)
                .username(username)
                .startedAt(Instant.now())
                .timeLimitMinutes(quiz.getTimeLimit() == null ? 0 : quiz.getTimeLimit())
                .totalQuestions(quiz.getQuestions() == null ? 0 : quiz.getQuestions().size())
                .build();

        attempt = attemptRepo.save(attempt);
        return new StartAttemptResponse(attempt.getId(), quiz.getId(), attempt.getStartedAt(), attempt.getTimeLimitMinutes());
    }

    @Override
    public SubmitResult submit(SubmitRequest request, String username) {
        Attempt attempt = attemptRepo.findById(request.attemptId())
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        if (!attempt.getUsername().equals(username))
            throw new RuntimeException("Attempt belongs to another user");

        // timer enforcement
        if (attempt.getTimeLimitMinutes() != null && attempt.getTimeLimitMinutes() > 0) {
            Instant deadline = attempt.getStartedAt().plus(Duration.ofMinutes(attempt.getTimeLimitMinutes()));
            if (Instant.now().isAfter(deadline)) {
                // Option A: reject late
                throw new RuntimeException("Time is over for this attempt");
                // Option B (alternative): grade anyway but mark late – customize here
            }
        }

        int correct = 0;
        for (var ans : request.answers()) {
            Question q = questionRepo.findById(ans.questionId())
                    .orElseThrow(() -> new RuntimeException("Question not found: " + ans.questionId()));
            OptionChoice chosen = optionRepo.findById(ans.optionId())
                    .orElseThrow(() -> new RuntimeException("Option not found: " + ans.optionId()));

            boolean isCorrect = chosen.isCorrect();

            AttemptAnswer aa = AttemptAnswer.builder()
                    .attempt(attempt)
                    .question(q)
                    .selectedOption(chosen)
                    .correct(isCorrect)
                    .build();
            attemptAnswerRepo.save(aa);

            if (isCorrect) correct++;
        }

        attempt.setSubmittedAt(Instant.now());
        attempt.setCorrectCount(correct);
        attempt.setScore(correct); // customize scoring if needed
        attemptRepo.save(attempt);

        return new SubmitResult(attempt.getId(), attempt.getTotalQuestions(), correct, attempt.getScore());
    }

    @Override
    public Attempt getAttempt(Long attemptId) {
        return attemptRepo.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));
    }
}

