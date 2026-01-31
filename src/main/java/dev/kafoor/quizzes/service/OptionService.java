package dev.kafoor.quizzes.service;

import dev.kafoor.quizzes.dto.v1.internal.OptionCreate;
import dev.kafoor.quizzes.dto.v1.internal.OptionUpdate;
import dev.kafoor.quizzes.entity.OptionEntity;
import dev.kafoor.quizzes.entity.QuestionEntity;
import dev.kafoor.quizzes.entity.QuestionsOptionEntity;
import dev.kafoor.quizzes.exception.NotFound;
import dev.kafoor.quizzes.repository.OptionRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OptionService {
    private final OptionRepo optionRepo;
    private final QuestionService questionService;
    private final QuestionsOptionService questionsOptionService;

    public Optional<OptionEntity> findOptionById(long id) {
        return optionRepo.findById(id);
    }

    public OptionEntity findOptionByIdOrThrow(long id) {
        return optionRepo.findById(id).orElseThrow(() -> new NotFound("option not found by id"));
    }

    public Optional<OptionEntity> findOptionBySlug(String slug) {
        return optionRepo.findBySlug(slug);
    }

    public OptionEntity findOptionBySlugOrThrow(String slug) {
        return optionRepo.findBySlug(slug).orElseThrow(() -> new NotFound("option not found by slug"));
    }

    public boolean existsOptionById(long id) {
        return optionRepo.existsById(id);
    }

    public boolean existsOptionBySlug(String slug) {
        return optionRepo.existsBySlug(slug);
    }

    @Transactional
    public OptionEntity addOptionToQuestion(OptionCreate dto) {
        QuestionEntity questionEntity = questionService.findQuestionBySlugOrThrow(dto.getQuestionSlug());
        OptionEntity newOption = OptionEntity.builder()
                .text(dto.getText())
                .slug(dto.getSlug())
                .build();
        OptionEntity createdOption = optionRepo.save(newOption);
        QuestionsOptionEntity newQuestionsOption = QuestionsOptionEntity.builder()
                .question(questionEntity)
                .option(newOption)
                .isCorrect(dto.isCorrect())
                .build();
        questionsOptionService.save(newQuestionsOption);
        return newOption;
    }

    @Transactional
    public OptionEntity updateOption(OptionUpdate dto) {
        Optional<OptionEntity> optionEntity = findOptionBySlug(dto.getSlug());
        QuestionEntity questionEntity = questionService.findQuestionBySlugOrThrow(dto.getQuestionSlug());

        if (optionEntity.isEmpty()) {
            OptionCreate optionCreate = OptionCreate.builder()
                    .slug(dto.getSlug())
                    .questionSlug(dto.getQuestionSlug())
                    .text(dto.getText())
                    .correct(dto.isCorrect())
                    .build();
            return addOptionToQuestion(optionCreate);
        } else {
            OptionEntity existsOption = findOptionBySlug(dto.getSlug()).get();
            existsOption.setText(dto.getText());
            questionsOptionService.changeIsCorrect(questionEntity, optionEntity.get(), dto.isCorrect());
            return optionRepo.save(existsOption);
        }
    }

    public void deleteOptionById(long id) {
        if (!existsOptionById(id))
            throw new NotFound("option not found by id");
        optionRepo.deleteById(id);
    }

    @Transactional
    public void deleteOptionBySlug(String slug) {
        if (!existsOptionBySlug(slug))
            throw new NotFound("option not found by slug");
        optionRepo.deleteBySlug(slug);
    }
}
