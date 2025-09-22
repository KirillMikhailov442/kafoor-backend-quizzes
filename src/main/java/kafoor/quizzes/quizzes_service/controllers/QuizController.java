package kafoor.quizzes.quizzes_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafoor.quizzes.quizzes_service.dtos.QuizCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.QuizUpdateReqDTO;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Quiz", description = "Official quiz API")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzesOfUser(){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(quizService.findAllQuizzesOfUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getOneQuiz(@PathVariable(name = "id") long quizId){
        return ResponseEntity.ok(quizService.findQuizById(quizId));
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@Valid @RequestBody QuizCreateReqDTO dto){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(quizService.createQuiz(dto, userId));
    }

    @PutMapping
    public ResponseEntity<Quiz> updateQuiz(@Valid @RequestBody QuizUpdateReqDTO dto){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(quizService.updateQuiz(dto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuizById(long quizId){
        quizService.deleteQuizById(quizId);
        return ResponseEntity.ok("The quiz was successfully deleted");
    }
}
