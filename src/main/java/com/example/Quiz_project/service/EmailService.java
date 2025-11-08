// EmailService.java
package com.example.Quiz_project.service;

import com.example.Quiz_project.dto.*;

public interface EmailService {
    void sendUsingTemplate(SendEmailRequest req);
    void sendRegistrationEmail(RegistrationEmailRequest req);
    void sendQuizResultEmail(QuizResultEmailRequest req);
    void sendPasswordResetEmail(PasswordResetEmailRequest req);
    void sendMail(String to, String subject, String message);
    void sendEmail(String to, String subject, String template, Object data);
}

