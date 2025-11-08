package com.example.Quiz_project.repository;

import com.example.Quiz_project.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByUsername(String username);
}

