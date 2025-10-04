package kafoor.quizzes.quizzes_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafoor.quizzes.quizzes_service.dtos.QuestionCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.QuestionDTO;
import kafoor.quizzes.quizzes_service.dtos.QuestionUpdateDTO;
import kafoor.quizzes.quizzes_service.models.Question;
import kafoor.quizzes.quizzes_service.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Question", description = "Official question API")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/api/v1")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions-of-quiz/{id}")
    public ResponseEntity<List<QuestionDTO>> getAllQuestionsOfQuiz(long quizId){
        List<Question> questions = questionService.findAllQuestionsOfQuiz(quizId);
        return ResponseEntity.ok(questions.stream().map(QuestionDTO::new).toList());
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<QuestionDTO> getOneQuestion(@PathVariable(name = "id") long questionId){
        Question question = questionService.findQuestionById(questionId);
       return ResponseEntity.ok(new QuestionDTO(question));
    }

    @PostMapping("/questions")
    public ResponseEntity<QuestionDTO> createQuestion(@Valid @RequestBody QuestionCreateReqDTO dto){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(new QuestionDTO(questionService.createQuestion(dto, userId)));
    }

    @PutMapping("/questions")
    public ResponseEntity<QuestionDTO> updateQuestion(@Valid @RequestBody QuestionUpdateDTO dto){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(new QuestionDTO(questionService.updateQuestion(dto, userId)));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable(name = "id") long questionId){
        questionService.deleteQuestionById(questionId);
        return ResponseEntity.ok("The question was successfully deleted");
    }
}
