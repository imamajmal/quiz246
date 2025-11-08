package com.example.Quiz_project.repository;
import com.example.Quiz_project.entity.OptionChoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionChoice, Long> {}

