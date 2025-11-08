package com.example.Quiz_project.controller;

import com.example.Quiz_project.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quizresult")
public class QuizResultController {

    private final QuizResultService resultService;

    @PostMapping("/take/{id}/submit")
    public String submitQuiz(@PathVariable Long id,
                             @RequestParam Map<String, String> formData,
                             Model model) {

        Map<Long, Long> answers = new HashMap<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("q_")) {
                answers.put(Long.valueOf(key.substring(2)), Long.valueOf(value));
            }
        });

        // Save + Score
        var result = resultService.saveAttempt(id, "GuestUser", answers);

        model.addAttribute("result", result);
        return "quiz-result";
    }

    @GetMapping("/results")
    public String showAllResults(Model model) {
        model.addAttribute("results", resultService.getAllResults());
        return "quiz-results"; // table view
    }
}

