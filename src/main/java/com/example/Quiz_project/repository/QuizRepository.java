package com.example.Quiz_project.repository;

import com.example.Quiz_project.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findAllByOrderByIdAsc();

    @Query("""
        SELECT q FROM Quiz q
        LEFT JOIN FETCH q.questions qs
        LEFT JOIN FETCH qs.options
        WHERE q.id = :id
    """)
    Optional<Quiz> fetchFullQuiz(@Param("id") Long id);
}


