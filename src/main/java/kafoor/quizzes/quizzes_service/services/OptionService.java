package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.dtos.OptionCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.OptionUpdateReqDTO;
import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.models.Option;
import kafoor.quizzes.quizzes_service.models.Question;
import kafoor.quizzes.quizzes_service.models.QuestionsOption;
import kafoor.quizzes.quizzes_service.repositories.OptionRepo;
import kafoor.quizzes.quizzes_service.repositories.QuestionOptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

@Service
public class OptionService {
    @Autowired
    private OptionRepo optionRepo;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionOptionRepo questionOptionRepo;

    public List<Option> findAllOptionsOfQuestion(UUID questionId) {
        return questionOptionRepo.findOptionsByQuestionId(questionId);
    }

    public Option findOptionById(UUID id) {
        return optionRepo.findById(id).orElseThrow(() -> new NotFound("Option not found"));
    }

    @Transient
    public Option addOptionToQuestion(OptionCreateReqDTO dto) {
        Question question = questionService.findQuestionById(dto.getQuestionId());
        Option newOption = Option.builder()
                .text(dto.getText())
                .id(dto.getOptionId())
                .build();
        Option option = optionRepo.save(newOption);
        QuestionsOption newQuestionsOption = QuestionsOption.builder()
                .question(question)
                .option(option)
                .isCorrect(dto.isCorrect())
                .build();
        questionOptionRepo.save(newQuestionsOption);
        return option;
    }

    public void removeOptionFromQuestion(long questionId, long optionId) {

    }

    public Option updateOption(OptionUpdateReqDTO dto) {
        Option option = findOptionById(dto.getId());
        if (dto.getText() != null && dto.getText().isEmpty())
            option.setText(dto.getText());
        return optionRepo.save(option);
    }

    public void deleteOptionById(UUID id) {
        if (optionRepo.existsById(id))
            throw new NotFound("Option not found");
        optionRepo.deleteById(id);
    }
}
