package com.example.Quiz_project.service;

import com.example.Quiz_project.entity.QuizResult;

import java.util.List;
import java.util.Map;

public interface QuizResultService {

    int evaluateQuiz(Long quizId, Map<Long, Long> answers);

    QuizResult saveAttempt(Long quizId, String userName, Map<Long, Long> answers);

    List<QuizResult> getAllResults();

    List<QuizResult> getResultsByUser(String userName);
}

