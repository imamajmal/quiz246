package com.example.Quiz_project.repository;
import com.example.Quiz_project.entity.Question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
      List<Question> findByQuizId(Long quizId);

      @Query("""
    SELECT q FROM Question q
    LEFT JOIN FETCH q.options
    WHERE q.quiz.id = :quizId
""")
List<Question> findByQuizIdFetch(@Param("quizId") Long quizId);

}

