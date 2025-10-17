package kafoor.quizzes.quizzes_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kafoor.quizzes.quizzes_service.repositories.QuestionOptionRepo;

@Service
public class QuestionsOptionService {
    @Autowired
    private QuestionOptionRepo questionOptionRepo;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private OptionService optionService;

    public void changeIsCorrect() {
    }
}
