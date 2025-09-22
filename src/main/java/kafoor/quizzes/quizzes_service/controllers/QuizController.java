package kafoor.quizzes.quizzes_service.controllers;

import jakarta.validation.Valid;
import kafoor.quizzes.quizzes_service.dtos.QuizCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.QuizUpdateReqDTO;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzesOfUser(long userId){
        return ResponseEntity.ok(quizService.findAllQuizzesOfUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getOneQuiz(@PathVariable(name = "id") long quizId){
        return ResponseEntity.ok(quizService.findQuizById(quizId));
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@Valid @RequestBody QuizCreateReqDTO dto){
        return ResponseEntity.ok(quizService.createQuiz(dto));
    }

    @PutMapping
    public ResponseEntity<Quiz> updateQuiz(@Valid @RequestBody QuizUpdateReqDTO dto){
        return ResponseEntity.ok(quizService.updateQuiz(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuizById(long quizId){
        quizService.deleteQuizById(quizId);
        return ResponseEntity.ok("The quiz was successfully deleted");
    }
}
