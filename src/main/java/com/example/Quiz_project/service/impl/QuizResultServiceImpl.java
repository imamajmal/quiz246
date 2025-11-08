package com.example.Quiz_project.service.impl;

import com.example.Quiz_project.entity.*;
import com.example.Quiz_project.repository.QuizRepository;
import com.example.Quiz_project.repository.QuizResultRepository;
import com.example.Quiz_project.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizResultServiceImpl implements QuizResultService {

    private final QuizRepository quizRepo;
    private final QuizResultRepository resultRepo;

    @Override
    public int evaluateQuiz(Long quizId, Map<Long, Long> answers) {
        Quiz quiz = quizRepo.fetchFullQuiz(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz Not Found"));

        int score = 0;

        for (Question q : quiz.getQuestions()) {
            Long selected = answers.get(q.getId());
            if (selected == null) continue;

            for (OptionChoice op : q.getOptions()) {
                if (op.getId().equals(selected) && op.isCorrect()) {
                    score++;
                }
            }
        }
        return score;
    }

   @Override
public QuizResult saveAttempt(Long quizId, String userName, Map<Long, Long> answers) {

    // ✅ Load full quiz with questions & options
    Quiz quiz = quizRepo.fetchFullQuiz(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz Not Found"));

    int score = evaluateQuiz(quizId, answers);

    QuizResult result = QuizResult.builder()
            .userName(userName == null ? "Guest" : userName)
            .quiz(quiz)
            .score(score)
            .totalQuestions(quiz.getQuestions().size())  // ✅ Correct now
            .chosenAnswers(answers.toString())
            .submittedAt(LocalDateTime.now())
            .build();

    return resultRepo.save(result);
}


    @Override
    public List<QuizResult> getAllResults() {
        return resultRepo.findAllByOrderBySubmittedAtDesc();
    }

    @Override
    public List<QuizResult> getResultsByUser(String userName) {
        return resultRepo.findByUserNameOrderBySubmittedAtDesc(userName);
    }
}

