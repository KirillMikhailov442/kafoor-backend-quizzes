package dev.kafoor.quizzes.mapper;

import dev.kafoor.quizzes.dto.v1.internal.QuestionCreate;
import dev.kafoor.quizzes.dto.v1.internal.QuestionUpdate;
import dev.kafoor.quizzes.dto.v1.request.QuestionCreateRequest;
import dev.kafoor.quizzes.dto.v1.request.QuestionUpdateRequest;
import dev.kafoor.quizzes.dto.v1.response.QuestionResponse;
import dev.kafoor.quizzes.dto.v1.response.QuestionsOptionResponse;
import dev.kafoor.quizzes.entity.QuestionEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {
    @Autowired
    private QuestionsOptionMapper optionMapper;

    public QuestionResponse toQuestionResponse(QuestionEntity question){
        List<QuestionsOptionResponse> options = question.getOptions()
                .stream()
                .map(el -> optionMapper.toQuestionsOptionResponse(el))
                .toList();

        return QuestionResponse.builder()
                .id(question.getId())
                .slug(question.getSlug())
                .scores(question.getScores())
                .text(question.getText())
                .timeLimit(question.getTimelimit())
                .options(options)
                .build();
    }

    public QuestionCreate toQuestionCreate(QuestionCreateRequest request){
        return QuestionCreate.builder()
                .quizId(request.getQuizId())
                .slug(request.getSlug())
                .text(request.getText())
                .timeLimit(request.getTimeLimit())
                .scores(request.getScores())
                .build();
    }

    public QuestionUpdate toQuestionUpdate(QuestionUpdateRequest request){
        return QuestionUpdate.builder()
                .quizId(request.getQuizId())
                .slug(request.getSlug())
                .text(request.getText())
                .timeLimit(request.getTimeLimit())
                .scores(request.getScores())
                .build();
    }
}
