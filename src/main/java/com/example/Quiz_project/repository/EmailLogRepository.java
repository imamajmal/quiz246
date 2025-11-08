// EmailLogRepository.java
package com.example.Quiz_project.repository;
import com.example.Quiz_project.entity.EmailLog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
    List<EmailLog> findAllByOrderBySentAtDesc();
}

