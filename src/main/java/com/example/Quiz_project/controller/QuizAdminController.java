package com.example.Quiz_project.controller;

import com.example.Quiz_project.entity.*;
import com.example.Quiz_project.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/quizzes")
@RequiredArgsConstructor
public class QuizAdminController {

    private final QuizService service;

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return service.createQuiz(quiz);
    }

    @GetMapping
    public List<Quiz> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return service.getQuiz(id);
    }

    @PutMapping("/{id}")
    public Quiz updateQuiz(@PathVariable Long id, @RequestBody Quiz q) {
        return service.updateQuiz(id, q);
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        service.deleteQuiz(id);
    }

    @PostMapping("/{quizId}/questions")
    public Question addQuestion(@PathVariable Long quizId, @RequestBody Question q) {
        return service.addQuestion(quizId, q);
    }

    @PutMapping("/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId, @RequestBody Question q) {
        return service.updateQuestion(questionId, q);
    }

    @DeleteMapping("/questions/{questionId}")
    public void deleteQuestion(@PathVariable Long questionId) {
        service.deleteQuestion(questionId);
    }

    @PostMapping("/questions/{questionId}/options")
    public OptionChoice addOption(@PathVariable Long questionId, @RequestBody OptionChoice o) {
        return service.addOption(questionId, o);
    }

    @DeleteMapping("/options/{optionId}")
    public void deleteOption(@PathVariable Long optionId) {
        service.deleteOption(optionId);
    }
}

