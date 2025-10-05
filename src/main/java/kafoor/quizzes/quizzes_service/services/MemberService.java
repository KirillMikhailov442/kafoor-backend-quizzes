package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.exceptions.Conflict;
import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.models.Member;
import kafoor.quizzes.quizzes_service.models.MemberAnswer;
import kafoor.quizzes.quizzes_service.models.Option;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.repositories.MemberAnswerRepo;
import kafoor.quizzes.quizzes_service.repositories.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private MemberAnswerRepo memberAnswerRepo;

    public Member findMemberById(long id){
        return memberRepo.findById(id).orElseThrow(() -> new NotFound("Member not found"));
    }

    public boolean existMemberInQuiz(Quiz quiz, long memberId){
        if(quiz.getMembers().isEmpty()) return false;
        return quiz.getMembers().stream().allMatch(member -> member.getId() == memberId);
    }

    public Member addMember(Quiz quiz, long memberId){
        if(existMemberInQuiz(quiz, memberId)) throw new Conflict("You have already been added to the quiz");
        if(quiz.getMembers().size() >= quiz.getMaxMember()) throw new Conflict("There is no more room in the quiz");

        Member newMember = Member.builder()
                .userId(memberId)
                .quiz(quiz)
                .build();
        return memberRepo.save(newMember);
    }

    public List<Member> addMembers(Quiz quiz, List<Long> membersId){
        List<Member> members = membersId.stream().map(member -> {
            if(existMemberInQuiz(quiz, member)) throw new Conflict("You have already been added to the quiz");
            if(quiz.getMembers().size() >= quiz.getMaxMember()) throw new Conflict("There is no more room in the quiz");

            return Member.builder()
                    .userId(member)
                    .quiz(quiz)
                    .build();
        }).toList();
        return memberRepo.saveAll(members);
    }

    public void removeMember(long memberId){
        if(memberRepo.existsById(memberId)) throw new NotFound("Member not found");
        memberRepo.deleteById(memberId);
    }

    public void answerMember(long memberId, long optionId){
    }
}
