package dev.kafoor.quizzes.repository;

import dev.kafoor.quizzes.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepo extends JpaRepository<OptionEntity, Long> {
    boolean existsBySlug(String slug);

    Optional<OptionEntity> findBySlug(String slug);

    void deleteBySlug(String slug);
}