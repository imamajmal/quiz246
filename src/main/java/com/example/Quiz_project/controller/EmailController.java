// EmailController.java
package com.example.Quiz_project.controller;

import com.example.Quiz_project.dto.*;
import com.example.Quiz_project.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    // Generic sender using any template by code
    @PostMapping("/send")
    public void sendGeneric(@RequestBody SendEmailRequest req) {
        emailService.sendUsingTemplate(req);
    }

    // Registration confirmation (permitAll OK)
    @PostMapping("/registration")
    public void registration(@RequestBody RegistrationEmailRequest req) {
        emailService.sendRegistrationEmail(req);
    }

    // Quiz result (Admin typically triggers)
    @PostMapping("/quiz-result")
    public void quizResult(@RequestBody QuizResultEmailRequest req) {
        emailService.sendQuizResultEmail(req);
    }

    // Password reset (permitAll)
    @PostMapping("/password-reset")
    public void passwordReset(@RequestBody PasswordResetEmailRequest req) {
        emailService.sendPasswordResetEmail(req);
    }

    @GetMapping("/reset-password")
public String verifyReset(@RequestParam String token) {
    return "Reset Page Loaded for token: " + token;
}


    
}

