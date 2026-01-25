package dev.kafoor.quizzes.repository;

import dev.kafoor.quizzes.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepo extends JpaRepository<QuizEntity, Long> {
    List<QuizEntity> findByUserId(long userId);
}
