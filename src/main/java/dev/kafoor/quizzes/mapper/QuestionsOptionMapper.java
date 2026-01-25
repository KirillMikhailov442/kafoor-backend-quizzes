package dev.kafoor.quizzes.mapper;

import dev.kafoor.quizzes.dto.v1.response.QuestionsOptionResponse;
import dev.kafoor.quizzes.entity.QuestionsOptionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionsOptionMapper {
    default QuestionsOptionResponse toQuestionsOptionResponse(QuestionsOptionEntity entity){
        return QuestionsOptionResponse.builder()
                .id(entity.getId())
                .slug(entity.getOption().getSlug())
                .text(entity.getOption().getText())
                .correct(entity.isCorrect())
                .img(entity.getOption().getImg())
                .build();
    }

    default List<QuestionsOptionResponse> toQuestionsOptionResponse(List<QuestionsOptionEntity> entities){
        return entities.stream()
                .map(el -> QuestionsOptionResponse.builder()
                .id(el.getId())
                .slug(el.getOption().getSlug())
                .text(el.getOption().getText())
                .correct(el.isCorrect())
                .img(el.getOption().getImg())
                .build()).toList();
    }
}
