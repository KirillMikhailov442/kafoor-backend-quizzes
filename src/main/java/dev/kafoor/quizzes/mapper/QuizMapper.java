package dev.kafoor.quizzes.mapper;

import dev.kafoor.quizzes.dto.v1.internal.QuizCreate;
import dev.kafoor.quizzes.dto.v1.internal.QuizStart;
import dev.kafoor.quizzes.dto.v1.internal.QuizUpdate;
import dev.kafoor.quizzes.dto.v1.request.QuizCreateRequest;
import dev.kafoor.quizzes.dto.v1.request.QuizStartRequest;
import dev.kafoor.quizzes.dto.v1.request.QuizUpdateRequest;
import dev.kafoor.quizzes.dto.v1.response.MemberResponse;
import dev.kafoor.quizzes.dto.v1.response.QuestionResponse;
import dev.kafoor.quizzes.dto.v1.response.QuizResponse;
import dev.kafoor.quizzes.entity.QuizEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuizMapper {
        @Autowired
        private QuestionMapper questionMapper;

        public QuizResponse toQuizResponse(QuizEntity quiz) {
                List<MemberResponse> memberResponses = quiz.getMembers().stream().map(el -> MemberResponse
                                .builder()
                                .id(el.getId())
                                .userId(el.getUserId())
                                .createdAt(el.getCreatedAt())
                                .build()).toList();

                List<QuestionResponse> questionResponses = quiz.getQuestions().stream()
                                .map(el -> questionMapper.toQuestionResponse(el))
                                .toList();

                return QuizResponse.builder()
                                .id(quiz.getId())
                                .name(quiz.getName())
                                .userId(quiz.getUserId())
                                .maxMembers(quiz.getMaxMembers())
                                .members(memberResponses)
                                .createdAt(quiz.getCreatedAt())
                                .updatedAt(quiz.getUpdatedAt())
                                .startedAt(quiz.getStartedAt())
                                .endedAt(quiz.getEndedAt())
                                .questions(questionResponses)
                                .build();
        }

        public QuizCreate toQuizCreate(QuizCreateRequest request) {
                return QuizCreate.builder()
                                .name(request.getName())
                                .maxMembers(request.getMaxMembers())
                                .build();
        }

        public QuizUpdate toQuizUpdate(QuizUpdateRequest request) {
                return QuizUpdate.builder()
                                .id(request.getId())
                                .name(request.getName())
                                .maxMembers(request.getMaxMembers())
                                .build();
        }

        public QuizStart toQuizStart(QuizStartRequest request) {
                return QuizStart.builder()
                                .users(request.getUsers())
                                .quizId(request.getQuizId())
                                .build();
        }
}
