package dev.kafoor.quizzes.service;

import dev.kafoor.quizzes.dto.v1.internal.QuizFinish;
import dev.kafoor.quizzes.dto.v1.internal.UserAnswer;
import dev.kafoor.quizzes.entity.MemberAnswerEntity;
import dev.kafoor.quizzes.entity.MemberEntity;
import dev.kafoor.quizzes.entity.QuestionsOptionEntity;
import dev.kafoor.quizzes.entity.QuizEntity;
import dev.kafoor.quizzes.exception.Conflict;
import dev.kafoor.quizzes.exception.NotFound;
import dev.kafoor.quizzes.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepo memberRepo;

    public List<MemberEntity> findAllMembersByUserId(long userId) {
        return memberRepo.findAllByUserId(userId);
    }

    public Optional<MemberEntity> findMemberById(long id) {
        return memberRepo.findById(id);
    }

    public MemberEntity findMemberByIdOrThrow(long id) {
        return memberRepo.findById(id).orElseThrow(() -> new NotFound("member not found by id"));
    }

    public Optional<MemberEntity> findFirstMemberById(long id) {
        return memberRepo.findFirstByUserId(id);
    }

    public MemberEntity findFirstMemberByIdOrThrow(long id) {
        return memberRepo.findFirstByUserId(id).orElseThrow(() -> new NotFound("member not found by id"));
    }

    public Optional<MemberEntity> findMemberByUserId(long userId) {
        return memberRepo.findByUserId(userId);
    }

    public MemberEntity findMemberByUserIdOrThrow(long userId) {
        return memberRepo.findByUserId(userId).orElseThrow(() -> new NotFound("member not found by id"));
    }

    public boolean existsMemberById(long id) {
        return memberRepo.existsById(id);
    }

    public boolean existMemberInQuiz(QuizEntity quiz, long userId) {
        if (quiz.getMembers().isEmpty())
            return false;
        return quiz.getMembers().stream().allMatch(member -> member.getUserId() == userId);
    }

    public MemberEntity addMember(QuizEntity quiz, long userId) {
        if (existMemberInQuiz(quiz, userId))
            throw new Conflict("you have already been added to the quiz");
        if (quiz.getMembers().size() >= quiz.getMaxMembers())
            throw new Conflict("there is no more room in the quiz");

        MemberEntity newMember = MemberEntity.builder()
                .userId(userId)
                .quiz(quiz)
                .build();
        return memberRepo.save(newMember);
    }

    public List<MemberEntity> addMembers(QuizEntity quiz, List<Long> membersId) {
        List<MemberEntity> members = membersId.stream().map(member -> MemberEntity.builder()
                .userId(member)
                .quiz(quiz)
                .build()).toList();
        return memberRepo.saveAll(members);
    }

    public void deleteMemberById(long memberId) {
        if (existsMemberById(memberId))
            throw new NotFound("member not found by id");
        memberRepo.deleteById(memberId);
    }
}
