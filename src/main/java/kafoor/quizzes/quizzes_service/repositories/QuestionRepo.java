package kafoor.quizzes.quizzes_service.repositories;

import kafoor.quizzes.quizzes_service.models.Question;
import kafoor.quizzes.quizzes_service.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepo extends JpaRepository<Question, Long> {
    public List<Question> findAllByQuiz(Quiz quiz);

    boolean existsBySlug(String slug);

    Optional<Question> findBySlug(String slug);

    void deleteBySlug(String slug);
}
