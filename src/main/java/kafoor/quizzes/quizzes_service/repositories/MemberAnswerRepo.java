package kafoor.quizzes.quizzes_service.repositories;

import kafoor.quizzes.quizzes_service.models.MemberAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAnswerRepo extends JpaRepository<MemberAnswer, Long> {
}
