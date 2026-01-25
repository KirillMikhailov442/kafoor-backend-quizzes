package dev.kafoor.quizzes.mapper;

import dev.kafoor.quizzes.dto.v1.internal.QuizCreate;
import dev.kafoor.quizzes.dto.v1.internal.QuizStart;
import dev.kafoor.quizzes.dto.v1.internal.QuizUpdate;
import dev.kafoor.quizzes.dto.v1.request.QuizCreateRequest;
import dev.kafoor.quizzes.dto.v1.request.QuizStartRequest;
import dev.kafoor.quizzes.dto.v1.request.QuizUpdateRequest;
import dev.kafoor.quizzes.dto.v1.response.MemberResponse;
import dev.kafoor.quizzes.dto.v1.response.QuizResponse;
import dev.kafoor.quizzes.entity.QuizEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    default QuizResponse toQuizResponse(QuizEntity quiz){
        List<MemberResponse> memberResponses = quiz.getMembers().stream().map(el -> MemberResponse
                .builder()
                .id(el.getId())
                .userId(el.getUserId())
                .createdAt(el.getCreatedAt())
                .build()
        ).toList();

        return QuizResponse.builder()
                .id(quiz.getId())
                .name(quiz.getName())
                .userId(quiz.getUserId())
                .maxMembers(quiz.getMaxMembers())
                .members(memberResponses)
                .build();
    }

    default QuizCreate toQuizCreate(QuizCreateRequest request){
        return QuizCreate.builder()
                .name(request.getName())
                .maxMembers(request.getMaxMembers())
                .build();
    }

    default QuizUpdate toQuizUpdate(QuizUpdateRequest request){
        return QuizUpdate.builder()
                .name(request.getName())
                .maxMembers(request.getMaxMembers())
                .build();
    }

    default QuizStart toQuizStart(QuizStartRequest request){
        return QuizStart.builder()
                .users(request.getUsers())
                .quizId(request.getQuizId())
                .build();
    }
}
