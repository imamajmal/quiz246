package com.example.Quiz_project.service.impl;

import com.example.Quiz_project.dto.*;
import com.example.Quiz_project.entity.*;
import com.example.Quiz_project.repository.*;
import com.example.Quiz_project.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;
    private final OptionRepository optionRepo;
    private final AttemptRepository attemptRepo;
    private final AttemptAnswerRepository attemptAnswerRepo;

    // --- mapping helpers ---
    private QuizViewDto toView(Quiz q) {
        return new QuizViewDto(
                q.getId(), q.getTitle(), q.getDescription(), q.getTimeLimit(),
                q.getQuestions().stream().map(qq ->
                        new QuizViewDto.QuestionDto(
                                qq.getId(), qq.getText(),
                                qq.getOptions().stream()
                                        .map(op -> new QuizViewDto.OptionDto(op.getId(), op.getText()))
                                        .toList()
                        )
                ).toList()
        );
    }

    @Override
    public List<QuizViewDto> listQuizzes() {
        return quizRepo.findAll().stream().map(this::toView).collect(Collectors.toList());
    }

    @Override
    public QuizViewDto getQuizForTaking(Long quizId) {
        Quiz q = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        return toView(q);
    }

    @Override
    public StartAttemptResponse startAttempt(Long quizId, String username) {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

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
    public SubmitResult submitAttempt(SubmitRequest request, String username) {
        Attempt attempt = attemptRepo.findById(request.attemptId())
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        if (!attempt.getUsername().equals(username))
            throw new RuntimeException("Attempt belongs to another user");

        // timer: if timeLimitMinutes>0, ensure within window
        if (attempt.getTimeLimitMinutes() != null && attempt.getTimeLimitMinutes() > 0) {
            Instant deadline = attempt.getStartedAt().plus(Duration.ofMinutes(attempt.getTimeLimitMinutes()));
            if (Instant.now().isAfter(deadline)) {
                // mark late submission as 0 or still evaluate — here we reject
                throw new RuntimeException("Time is over for this attempt");
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
        attempt.setScore(correct); // simple score == correct; adjust as needed
        attemptRepo.save(attempt);

        return new SubmitResult(attempt.getId(), attempt.getTotalQuestions(), attempt.getCorrectCount(), attempt.getScore());
    }

    @Override
    public Attempt getAttempt(Long attemptId) {
        return attemptRepo.findById(attemptId).orElseThrow(() -> new RuntimeException("Attempt not found"));
    }
}

