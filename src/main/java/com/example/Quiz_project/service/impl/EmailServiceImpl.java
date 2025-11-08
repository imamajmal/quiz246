// EmailServiceImpl.java
package com.example.Quiz_project.service.impl;

import com.example.Quiz_project.dto.*;
import com.example.Quiz_project.entity.EmailLog;
import com.example.Quiz_project.entity.EmailTemplate;
import com.example.Quiz_project.repository.EmailLogRepository;
import com.example.Quiz_project.repository.EmailTemplateRepository;
import com.example.Quiz_project.service.EmailService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailTemplateRepository templateRepo;
    private final EmailLogRepository logRepo;
    private final SpringTemplateEngine templateEngine;


    @Override
    public void sendUsingTemplate(SendEmailRequest req) {
        EmailTemplate tpl = templateRepo.findByCode(req.templateCode())
                .orElseThrow(() -> new RuntimeException("Template not found: " + req.templateCode()));
        String subject = replaceVars(tpl.getSubject(), req.variables());
        String body    = replaceVars(tpl.getBody(), req.variables());
        sendRawMail(req.to(), subject, body);
    }

    @Override
    public void sendRegistrationEmail(RegistrationEmailRequest req) {
        sendUsingTemplate(new SendEmailRequest(
                req.to(), "REG_CONFIRM",
                Map.of("name", req.name())
        ));
    }

    @Override
    public void sendQuizResultEmail(QuizResultEmailRequest req) {
        sendUsingTemplate(new SendEmailRequest(
                req.to(), "QUIZ_RESULT",
                Map.of(
                    "name", req.name(),
                    "quizTitle", req.quizTitle(),
                    "score", String.valueOf(req.score()),
                    "total", String.valueOf(req.total())
                )
        ));
    }

    @Override
    public void sendPasswordResetEmail(PasswordResetEmailRequest req) {
        sendUsingTemplate(new SendEmailRequest(
                req.to(), "RESET_PASSWORD",
                Map.of("name", req.name(), "resetLink", req.resetLink())
        ));
    }

    // -------- helpers --------
    private String replaceVars(String text, Map<String,String> vars) {
        if (text == null || vars == null) return text;
        String out = text;
        for (var e : vars.entrySet()) {
            out = out.replace("{{" + e.getKey() + "}}", e.getValue());
        }
        return out;
    }

    private void sendRawMail(String to, String subject, String body) {
        EmailLog log = EmailLog.builder()
                .toAddress(to).subject(subject).body(body)
                .sentAt(Instant.now())
                .success(false)
                .build();
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            log.setSuccess(true);
        } catch (Exception ex) {
            log.setError(ex.getMessage());
            throw new RuntimeException("Failed to send email: " + ex.getMessage(), ex);
        } finally {
            logRepo.save(log);
        }
    }

    @Override
    public void sendMail(String to, String subject, String message) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("imamajmal60@gmail.com");   // ✅ your gmail
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);

        mailSender.send(mail);
    }

      @Override
    public void sendEmail(String to, String subject, String template, Object data) {

        EmailLog log = new EmailLog();
        log.setToAddress(to);
        log.setSubject(subject);

        try {
            // ✅ process Thymeleaf template
            Context ctx = new Context();
            ctx.setVariable("data", data);
            String html = templateEngine.process(template, ctx);

            // ✅ prepare email
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            // ✅ send
            mailSender.send(msg);

            log.setBody(html);
            log.setSuccess(true);

        } catch (Exception e) {
            log.setSuccess(false);
            log.setError(e.getMessage());
        }

        log.setSentAt(Instant.now());
        logRepo.save(log);
    }
}

