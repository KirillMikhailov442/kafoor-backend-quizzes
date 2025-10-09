package kafoor.quizzes.quizzes_service.repositories;

import kafoor.quizzes.quizzes_service.models.Option;
import kafoor.quizzes.quizzes_service.models.Question;
import kafoor.quizzes.quizzes_service.models.QuestionsOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionOptionRepo extends JpaRepository<QuestionsOption, Long> {
    @Query("SELECT qo.option FROM QuestionsOption qo WHERE qo.question.id = :questionId")
    List<Option> findOptionsByQuestionId(@Param("questionId") long questionId);

    List<QuestionsOption> findAllByQuestion(Question question);

    List<QuestionsOption> findAllByOption(Option option);

    QuestionsOption findByQuestion(Question question);

    QuestionsOption findByOption(Option option);
}
