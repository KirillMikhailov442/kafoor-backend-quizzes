package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.models.Member;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.repositories.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepo memberRepo;

    public Member findMemberById(long id){
        return memberRepo.findById(id).orElseThrow(() -> new NotFound("Member not found"));
    }

    public Member addMember(Quiz quiz, long memberId){
        Member newMember = Member.builder()
                .userId(memberId)
                .quiz(quiz)
                .build();
        return memberRepo.save(newMember);
    }

    public void removeMember(long memberId){
        if(memberRepo.existsById(memberId)) throw new NotFound("Member not found");
        memberRepo.deleteById(memberId);
    }
}
