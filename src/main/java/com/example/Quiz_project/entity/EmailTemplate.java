// EmailTemplate.java
package com.example.Quiz_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name="email_template")
public class EmailTemplate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // e.g. REG_CONFIRM, QUIZ_RESULT, RESET_PASSWORD
    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String body; // supports placeholders like {{name}} {{quizTitle}} {{score}}
}

