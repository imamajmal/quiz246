package com.example.Quiz_project.controller;

import com.example.Quiz_project.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailSendController {

    private final EmailService emailService;

    @GetMapping("/test")
    public String testMail() {
        emailService.sendMail(
                "imamdeen60@gmail.com",
                "Test Mail from Spring Boot",
                "✅ Your Spring Boot email service is working successfully!"
        );
        return "✅ Test email sent!";
    }

    @PostMapping("/send")
    public String sendDynamicMail(@RequestBody EmailRequest request) {
        emailService.sendMail(
                request.getTo(),
                request.getSubject(),
                request.getMessage()
        );
        return "✅ Email sent to: " + request.getTo();
    }

    public static class EmailRequest {
        private String to;
        private String subject;
        private String message;

        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }
        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}

