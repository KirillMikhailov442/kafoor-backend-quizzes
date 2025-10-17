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

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OptionService {
    @Autowired
    private OptionRepo optionRepo;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionOptionRepo questionOptionRepo;

    public List<Option> findAllOptionsOfQuestion(long questionId) {
        return questionOptionRepo.findOptionsByQuestionId(questionId);
    }

    public Option findOptionById(long id) {
        return optionRepo.findById(id).orElseThrow(() -> new NotFound("Option not found"));
    }

    @Transactional
    public Option addOptionToQuestion(OptionCreateReqDTO dto) {
        Question question = questionService.findQuestionBySlug(dto.getQuestionSlug());
        Option newOption = Option.builder()
                .text(dto.getText())
                .slug(dto.getSlug())
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

    @Transactional
    public Option updateOption(OptionUpdateReqDTO dto) {
        Optional<Option> option = optionRepo.findBySlug(dto.getSlug());
        if (option.isEmpty()) {
            OptionCreateReqDTO optionCreate = OptionCreateReqDTO.builder()
                    .slug(dto.getSlug())
                    .questionSlug(dto.getQuestionSlug())
                    .text(dto.getText())
                    .isCorrect(dto.isCorrect())
                    .build();
            return addOptionToQuestion(optionCreate);
        } else {
            Option exisOption = optionRepo.findBySlug(dto.getSlug()).get();
            if (dto.getText() != null && dto.getText().isEmpty())
                exisOption.setText(dto.getText());
            return optionRepo.save(exisOption);
        }
    }

    public void deleteOptionById(long id) {
        if (optionRepo.existsById(id))
            throw new NotFound("Option not found");
        optionRepo.deleteById(id);
    }

    public void deleteOptionBySlug(UUID slug) {
        if (optionRepo.existsBySlug(slug))
            throw new NotFound("Option not found");
        optionRepo.deleteBySlug(slug);
    }
}
