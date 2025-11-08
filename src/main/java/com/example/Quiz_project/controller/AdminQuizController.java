package com.example.Quiz_project.controller;

import com.example.Quiz_project.entity.Quiz;
import com.example.Quiz_project.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/adminview/quizzes")
@RequiredArgsConstructor
public class AdminQuizController {

    private final QuizService quizService;

    // ✅ Show all quizzes (quiz-list.html)
    @GetMapping
    public String listQuizzes(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quiz-list";
    }

    // ✅ Show Create Quiz Page
    @GetMapping("/create")
    public String createQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "quiz-create";
    }

    // ✅ Save new quiz
    @PostMapping("/save")
    public String saveQuiz(@ModelAttribute Quiz quiz) {
        quizService.saveQuiz(quiz);
        return "redirect:/adminview/quizzes"; // go back to list
    }

    // ✅ Show Edit Page
    @GetMapping("/edit/{id}")
    public String editQuiz(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.getQuizById(id);
        model.addAttribute("quiz", quiz);
        return "quiz-edit";
    }

    // ✅ Update quiz
    @PostMapping("/update/{id}")
    public String updateQuiz(@PathVariable Long id, @ModelAttribute Quiz quiz) {
        quizService.updateQuiz(id, quiz);
        return "redirect:/adminview/quizzes";
    }

    // ✅ Delete quiz
    @GetMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/adminview/quizzes";
    }



}

