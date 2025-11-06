package kafoor.quizzes.quizzes_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafoor.quizzes.quizzes_service.dtos.MemberReqDTO;
import kafoor.quizzes.quizzes_service.models.Member;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.services.MemberService;
import kafoor.quizzes.quizzes_service.services.QuizService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/api/v1")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private QuizService quizService;

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getOneMember(@PathVariable(name = "id") long memberId) {
        return ResponseEntity.ok(memberService.findMemberById(memberId));
    }

    @GetMapping("/members/quizzes")
    public ResponseEntity<List<Member>> getQuizzes() {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(memberService.findAllMembersByUserId(userId));
    }

    @PostMapping("/members")
    public ResponseEntity<String> addMemberToQuiz(@Valid @RequestBody MemberReqDTO dto) {
        Quiz quiz = quizService.findQuizById(dto.getQuizId());
        memberService.addMember(quiz, dto.getMemberId());
        return ResponseEntity.ok("The participant has been successfully added");
    }
}
