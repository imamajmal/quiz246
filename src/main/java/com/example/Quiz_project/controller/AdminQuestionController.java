package com.example.Quiz_project.controller;

import com.example.Quiz_project.entity.OptionChoice;
import com.example.Quiz_project.entity.Question;
import com.example.Quiz_project.entity.Quiz;
import com.example.Quiz_project.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin/question")
@RequiredArgsConstructor
public class AdminQuestionController {

    private final QuizService quizService;

    // ✅ LIST QUESTIONS OF A QUIZ
    @GetMapping("/list/{quizId}")
    public String listQuestions(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.getQuizById(quizId);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", quizService.getQuestions(quizId));
        return "question-list"; 
    }

    // ✅ SHOW CREATE QUESTION PAGE
    @GetMapping("/create/{quizId}")
    public String createQuestionForm(@PathVariable Long quizId, Model model) {
        model.addAttribute("quizId", quizId);
        return "question-create";
    }

    // ✅ SAVE NEW QUESTION + OPTIONS
    @PostMapping("/save")
    public String saveQuestion(
            @RequestParam Long quizId,
            @RequestParam String text,
            @RequestParam List<String> options,
            @RequestParam int correctIndex) {

        Quiz quiz = quizService.getQuizById(quizId);

        Question q = new Question();
        q.setText(text);
        q.setQuiz(quiz);

        List<OptionChoice> list = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            OptionChoice oc = new OptionChoice();
            oc.setText(options.get(i));
            oc.setCorrect((i + 1) == correctIndex);
            oc.setQuestion(q);
            list.add(oc);
        }

        // ✅ Convert List → Set
        q.setOptions(new HashSet<>(list));

        quizService.saveQuestion(q);
        return "redirect:/admin/question/list/" + quizId;
    }

    // ✅ DELETE QUESTION
    @GetMapping("/delete/{id}/{quizId}")
    public String deleteQuestion(@PathVariable Long id, @PathVariable Long quizId) {
        quizService.deleteQuestion(id);
        return "redirect:/admin/question/list/" + quizId;
    }

    // ✅ EDIT QUESTION PAGE
    @GetMapping("/edit/{questionId}")
    public String editQuestion(@PathVariable Long questionId, Model model) {
        Question question = quizService.getQuestionById(questionId);
        model.addAttribute("question", question);
        return "question-edit";
    }

    // ✅ UPDATE QUESTION + OPTIONS
    @PostMapping("/update/{questionId}")
    public String updateQuestion(@PathVariable Long questionId,
                                 @RequestParam String text,
                                 @RequestParam List<String> options,
                                 @RequestParam int correctIndex) {

        Question q = quizService.getQuestionById(questionId);
        q.setText(text);

        // ✅ Convert Set → List so we can index
        List<OptionChoice> existingOptions = new ArrayList<>(q.getOptions());

        for (int i = 0; i < existingOptions.size(); i++) {
            OptionChoice op = existingOptions.get(i);
            op.setText(options.get(i));
            op.setCorrect((i + 1) == correctIndex);
        }

        quizService.saveQuestion(q);

        return "redirect:/admin/question/list/" + q.getQuiz().getId();
    }

    // ✅ RETURN TO QUIZ LIST
    @GetMapping("/question-list")
    public String goBackToQuizList(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quiz-list"; 
    }
}
