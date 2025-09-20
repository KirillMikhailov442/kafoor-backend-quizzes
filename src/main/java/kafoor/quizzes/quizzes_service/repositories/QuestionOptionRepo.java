package kafoor.quizzes.quizzes_service.repositories;

import kafoor.quizzes.quizzes_service.models.QuestionsOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionOptionRepo extends JpaRepository<QuestionsOption, Long> {
}
