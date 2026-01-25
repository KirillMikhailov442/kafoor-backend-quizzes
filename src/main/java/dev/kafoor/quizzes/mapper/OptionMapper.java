package dev.kafoor.quizzes.mapper;

import dev.kafoor.quizzes.dto.v1.internal.OptionCreate;
import dev.kafoor.quizzes.dto.v1.internal.OptionUpdate;
import dev.kafoor.quizzes.dto.v1.request.OptionCreateRequest;
import dev.kafoor.quizzes.dto.v1.request.OptionUpdateRequest;
import dev.kafoor.quizzes.dto.v1.response.OptionResponse;
import dev.kafoor.quizzes.entity.OptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    default OptionResponse toOptionResponse(OptionEntity option){
        return OptionResponse.builder()
                .id(option.getId())
                .slug(option.getSlug())
                .text(option.getText())
                .build();
    }

    default OptionCreate toOptionCreate(OptionCreateRequest request){
        return OptionCreate.builder()
                .slug(request.getSlug())
                .questionSlug(request.getQuestionSlug())
                .text(request.getText())
                .correct(request.isCorrect())
                .build();
    }

    default OptionUpdate toOptionUpdate(OptionUpdateRequest request){
        return OptionUpdate.builder()
                .slug(request.getSlug())
                .questionSlug(request.getQuestionSlug())
                .text(request.getText())
                .correct(request.isCorrect())
                .build();
    }
}
