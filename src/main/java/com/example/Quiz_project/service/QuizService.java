package com.example.Quiz_project.service;

import com.example.Quiz_project.entity.Quiz;
import com.example.Quiz_project.entity.Question;
import com.example.Quiz_project.entity.OptionChoice;

import java.util.List;
import java.util.Map;

public interface QuizService {
    Quiz createQuiz(Quiz quiz);
    List<Quiz> getAll();
    Quiz getQuiz(Long id);
    Quiz updateQuiz(Long id, Quiz quiz);
    void deleteQuiz(Long id);

    Question addQuestion(Long quizId, Question question);
    Question updateQuestion(Long questionId, Question question);
    void deleteQuestion(Long questionId);

    OptionChoice addOption(Long questionId, OptionChoice option);
    void deleteOption(Long optionId);

      List<Quiz> getAllQuizzes();
    Quiz saveQuiz(Quiz quiz);
    Quiz getQuizById(Long id);
   

    List<Question> getQuestions(Long quizId);
    Question saveQuestion(Question question);

       Question getQuestionById(Long id);

    List<Quiz> listAll();
    Quiz loadQuiz(Long id);
    int totalQuestions(Long id);
    int gradeQuiz(Long id, Map<Long, Long> answers);

   
    
}

