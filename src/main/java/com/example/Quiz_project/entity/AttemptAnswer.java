package com.example.Quiz_project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttemptAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attempt_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)      // ✅ delete attempt answers if attempt is deleted
    private Attempt attempt;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)      // ✅ delete attempt answers if question is deleted
    private Question question;

    @ManyToOne
    @JoinColumn(name = "selected_option_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)      // ✅ delete attempt answers if selected option is deleted
    private OptionChoice selectedOption;

    private boolean correct;
}



