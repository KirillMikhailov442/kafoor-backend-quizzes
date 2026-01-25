package dev.kafoor.quizzes.repository;

import dev.kafoor.quizzes.entity.OptionEntity;
import dev.kafoor.quizzes.entity.QuestionEntity;
import dev.kafoor.quizzes.entity.QuestionsOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionOptionRepo extends JpaRepository<QuestionsOptionEntity, Long> {
    @Query("SELECT o FROM QuestionsOptionEntity qo " +
            "JOIN qo.option o " +
            "WHERE qo.question.id = :questionId")
    List<OptionEntity> findOptionsByQuestionId(@Param("questionId") long questionId);

    Optional<QuestionsOptionEntity> findByQuestionIdAndOptionId(long questionId, long optionId);

    List<QuestionsOptionEntity> findAllByQuestion(QuestionEntity question);

    List<QuestionsOptionEntity> findAllByOption(OptionEntity option);

    Optional<QuestionsOptionEntity> findByQuestion(QuestionEntity question);

    Optional<QuestionsOptionEntity> findByOption(OptionEntity option);

    Optional<QuestionsOptionEntity> findByQuestionAndOption(QuestionEntity question, OptionEntity option);
}