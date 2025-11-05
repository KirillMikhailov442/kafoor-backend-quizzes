package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.models.Option;
import kafoor.quizzes.quizzes_service.models.Question;
import kafoor.quizzes.quizzes_service.models.QuestionsOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kafoor.quizzes.quizzes_service.repositories.QuestionOptionRepo;

import java.util.List;

@Service
public class QuestionsOptionService {
    @Autowired
    private QuestionOptionRepo questionOptionRepo;

    public List<Option> findAllOptionsOfQuestion(long questionId) {
        return questionOptionRepo.findOptionsByQuestionId(questionId);
    }

    public QuestionsOption findByQuestionIdAndOptionId(long questionId, long optionId) {
        return questionOptionRepo.findByQuestionIdAndOptionId(questionId, optionId);
    }

    public void changeIsCorrect(Question question, Option option, boolean isCorrect) {
        QuestionsOption questionsOption = questionOptionRepo.findByQuestionAndOption(question, option);
        questionsOption.setCorrect(isCorrect);
        System.out.println(questionsOption.getQuestion().getSlug());
        System.out.println(questionsOption.getOption().getSlug());
        questionOptionRepo.save(questionsOption);
    }

    public void save(QuestionsOption questionsOption) {
        questionOptionRepo.save(questionsOption);
    }
}
