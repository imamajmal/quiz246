package com.example.Quiz_project.repository;

import com.example.Quiz_project.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findAllByOrderBySubmittedAtDesc();

    List<QuizResult> findByUserNameOrderBySubmittedAtDesc(String userName);
}

