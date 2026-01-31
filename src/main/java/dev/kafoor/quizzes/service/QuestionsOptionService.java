package dev.kafoor.quizzes.service;

import dev.kafoor.quizzes.dto.v1.internal.QuestionCreate;
import dev.kafoor.quizzes.entity.OptionEntity;
import dev.kafoor.quizzes.entity.QuestionEntity;
import dev.kafoor.quizzes.entity.QuestionsOptionEntity;
import dev.kafoor.quizzes.entity.QuizEntity;
import dev.kafoor.quizzes.exception.NotFound;
import dev.kafoor.quizzes.repository.QuestionOptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionsOptionService {
    private final QuestionOptionRepo questionOptionRepo;

    public List<OptionEntity> findAllOptionsOfQuestion(long questionId) {
        return questionOptionRepo.findOptionsByQuestionId(questionId);
    }

    public Optional<QuestionsOptionEntity> findByQuestionIdAndOptionId(long questionId, long optionId) {
        return questionOptionRepo.findByQuestionIdAndOptionId(questionId, optionId);
    }

    public QuestionsOptionEntity findByQuestionIdAndOptionIdOrThrow(long questionId, long optionId) {
        return questionOptionRepo.findByQuestionIdAndOptionId(questionId, optionId)
                .orElseThrow(() -> new NotFound("questions or options not found"));
    }

    public Optional<QuestionsOptionEntity> findByQuestionAndOption(QuestionEntity question, OptionEntity option) {
        return questionOptionRepo.findByQuestionAndOption(question, option);
    }

    public QuestionsOptionEntity findByQuestionAndOptionOrThrow(QuestionEntity question, OptionEntity option) {
        return questionOptionRepo.findByQuestionAndOption(question, option)
                .orElseThrow(() -> new NotFound("questions or options not found"));
    }

    public void changeIsCorrect(QuestionEntity question, OptionEntity option, boolean isCorrect) {
        QuestionsOptionEntity questionsOption = findByQuestionAndOptionOrThrow(question, option);
        questionsOption.setCorrect(isCorrect);
        questionOptionRepo.save(questionsOption);
    }

    public void save(QuestionsOptionEntity questionsOption) {
        questionOptionRepo.save(questionsOption);
    }
}
