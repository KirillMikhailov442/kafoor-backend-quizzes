package dev.kafoor.quizzes.repository;

import dev.kafoor.quizzes.entity.QuestionEntity;
import dev.kafoor.quizzes.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepo extends JpaRepository<QuestionEntity, Long> {
    public List<QuestionEntity> findAllByQuiz(QuizEntity quiz);

    boolean existsBySlug(String slug);

    Optional<QuestionEntity> findBySlug(String slug);

    void deleteBySlug(String slug);
}
