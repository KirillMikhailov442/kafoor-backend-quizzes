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

@Service
public class OptionService {
    @Autowired
    private OptionRepo optionRepo;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionsOptionService QOService;

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
        QOService.save(newQuestionsOption);
        return option;
    }

    public void removeOptionFromQuestion(long questionId, long optionId) {

    }

    @Transactional
    public Option updateOption(OptionUpdateReqDTO dto) {
        Optional<Option> option = optionRepo.findBySlug(dto.getSlug());
        Question question = questionService.findQuestionBySlug(dto.getQuestionSlug());

        if (option.isEmpty()) {
            OptionCreateReqDTO optionCreate = OptionCreateReqDTO.builder()
                    .slug(dto.getSlug())
                    .questionSlug(dto.getQuestionSlug())
                    .text(dto.getText())
                    .correct(dto.isCorrect())
                    .build();
            return addOptionToQuestion(optionCreate);
        } else {
            Option exisOption = optionRepo.findBySlug(dto.getSlug()).get();
            exisOption.setText(dto.getText());
            QOService.changeIsCorrect(question, option.get(), dto.isCorrect());
            return optionRepo.save(exisOption);
        }
    }

    public void deleteOptionById(long id) {
        if (!optionRepo.existsById(id))
            throw new NotFound("Option not found");
        optionRepo.deleteById(id);
    }

    @Transactional
    public void deleteOptionBySlug(String slug) {
        if (!optionRepo.existsBySlug(slug))
            throw new NotFound("Option not found");
        optionRepo.deleteBySlug(slug);
    }
}
