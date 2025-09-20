package kafoor.quizzes.quizzes_service.repositories;

import kafoor.quizzes.quizzes_service.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepo extends JpaRepository<Quiz, Long> {
    public List<Quiz> findByUserId(long userId);
}
