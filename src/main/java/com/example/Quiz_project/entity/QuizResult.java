package com.example.Quiz_project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "quiz_result")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private int score;

    // ✅ IMPORTANT: map this to column "total"
    @Column(name = "total")
    private int totalQuestions;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "chosen_answers", columnDefinition = "TEXT")
    private String chosenAnswers;
}
