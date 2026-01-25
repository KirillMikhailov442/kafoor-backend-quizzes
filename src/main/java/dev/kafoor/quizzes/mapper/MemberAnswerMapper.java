package dev.kafoor.quizzes.mapper;

import dev.kafoor.quizzes.dto.v1.internal.QuizFinish;
import dev.kafoor.quizzes.dto.v1.internal.UserAnswer;
import dev.kafoor.quizzes.dto.v1.request.QuizFinishRequest;
import dev.kafoor.quizzes.dto.v1.response.MemberAnswerResponse;
import dev.kafoor.quizzes.entity.MemberAnswerEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class MemberAnswerMapper {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private QuestionsOptionMapper questionsOptionMapper;

    public MemberAnswerResponse toAnswerResponse(MemberAnswerEntity entity){
        return MemberAnswerResponse.builder()
                .id(entity.getId())
                .member(memberMapper.toMemberResponse(entity.getMember()))
                .answer(questionsOptionMapper.toQuestionsOptionResponse(entity.getAnswer()))
                .build();
    }

    public QuizFinish toQuizFinish(QuizFinishRequest request){
        Map<Long, List<UserAnswer>> answers = new HashMap<>();
        request.getAnswers().forEach((key, value) -> {
            List<UserAnswer> userAnswers = value.stream().map(userAnswer ->
                    UserAnswer.builder()
                            .questionID(userAnswer.getQuestionId())
                            .answer(userAnswer.getAnswer())
                            .nickname(userAnswer.getNickname())
                            .build()
            ).toList();
            answers.put(key, userAnswers);
        });
        return QuizFinish.builder()
                .quizId(request.getQuizId())
                .answers(answers)
                .build();
    }
}
