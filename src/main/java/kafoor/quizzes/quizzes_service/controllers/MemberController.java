package kafoor.quizzes.quizzes_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafoor.quizzes.quizzes_service.dtos.MemberReqDTO;
import kafoor.quizzes.quizzes_service.models.Member;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.services.MemberService;
import kafoor.quizzes.quizzes_service.services.QuizService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "Official member API")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/api/v1")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private QuizService quizService;

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getOneMember(@PathVariable(name = "id") UUID memberId) {
        return ResponseEntity.ok(memberService.findMemberById(memberId));
    }

    @PostMapping("/members")
    public ResponseEntity<String> addMemberToQuiz(@Valid @RequestBody MemberReqDTO dto) {
        Quiz quiz = quizService.findQuizById(dto.getQuizId());
        memberService.addMember(quiz, dto.getMemberId());
        return ResponseEntity.ok("The participant has been successfully added");
    }
}
