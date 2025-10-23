package kafoor.quizzes.quizzes_service.repositories;

import kafoor.quizzes.quizzes_service.models.Option;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OptionRepo extends JpaRepository<Option, Long> {
    boolean existsBySlug(String slug);

    Optional<Option> findBySlug(String slug);

    void deleteBySlug(String slug);
}
