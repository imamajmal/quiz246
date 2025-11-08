package com.example.Quiz_project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Attempt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // who took the quiz (username is enough; you can map User if you prefer)
    private String username;

    @ManyToOne @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private Instant startedAt;
    private Instant submittedAt;

    // cached quiz time limit (minutes) at start
    private Integer timeLimitMinutes;

    private Integer totalQuestions;
    private Integer correctCount;
    private Integer score; // optional

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AttemptAnswer> answers = new ArrayList<>();
}

