package dev.kafoor.quizzes.service;

import dev.kafoor.quizzes.dto.v1.internal.QuizFinish;
import dev.kafoor.quizzes.dto.v1.internal.UserAnswer;
import dev.kafoor.quizzes.entity.MemberAnswerEntity;
import dev.kafoor.quizzes.entity.MemberEntity;
import dev.kafoor.quizzes.entity.QuestionsOptionEntity;
import dev.kafoor.quizzes.repository.MemberAnswerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberAnswerService {
    private final MemberAnswerRepo memberAnswerRepo;
    private final MemberService memberService;
    private final QuestionsOptionService questionsOptionService;

    public List<MemberAnswerEntity> findAnswersByListId(List<Long> membersId) {
        return memberAnswerRepo.findByMemberIdIn(membersId);
    }

    public void addAnswersMember(QuizFinish dto) {
        List<MemberAnswerEntity> memberAnswers = new ArrayList<>();

        dto.getAnswers().forEach((userId, answers) -> {
            MemberEntity member = memberService.findFirstMemberByIdOrThrow(userId);
            for (UserAnswer userAnswer : answers) {
                QuestionsOptionEntity answer = questionsOptionService.findByQuestionIdAndOptionIdOrThrow(
                        userAnswer.getQuestionID(),
                        userAnswer.getAnswer());
                MemberAnswerEntity memberAnswerEntity = MemberAnswerEntity.builder()
                        .member(member)
                        .answer(answer)
                        .build();

                memberAnswers.add(memberAnswerEntity);
            }
        });
        memberAnswerRepo.saveAll(memberAnswers);
    }
}
