// EmailLog.java
package com.example.Quiz_project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name="email_log")
public class EmailLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String toAddress;
    private String subject;

    @Lob
    private String body;

    private Instant sentAt;
    private boolean success;

    @Lob
    private String error; // store failure reason when success=false
}

