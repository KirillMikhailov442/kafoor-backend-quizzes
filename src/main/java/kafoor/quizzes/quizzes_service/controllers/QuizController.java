package kafoor.quizzes.quizzes_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafoor.quizzes.quizzes_service.configs.SocketIOConfig;
import kafoor.quizzes.quizzes_service.constants.SocketAction;
import kafoor.quizzes.quizzes_service.dtos.QuizCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.QuizDTO;
import kafoor.quizzes.quizzes_service.dtos.QuizStartDTO;
import kafoor.quizzes.quizzes_service.dtos.QuizUpdateReqDTO;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.services.QuizService;
import kafoor.quizzes.quizzes_service.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Quiz")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private SocketIOConfig socketIOConfig;
    @Autowired
    private RedisService redisService;

    @GetMapping("/mine")
    public ResponseEntity<List<QuizDTO>> getAllQuizzesOfUser() {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Quiz> quizzes = quizService.findAllQuizzesOfUser(userId);
        List<QuizDTO> quizDTOS = quizzes.stream().map(QuizDTO::new).toList();
        return ResponseEntity.ok(quizDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getOneQuiz(@PathVariable(name = "id") long quizId) {
        Quiz quiz = quizService.findQuizById(quizId);
        QuizDTO quizDTO = new QuizDTO(quiz);
        return ResponseEntity.ok(quizDTO);
    }

    @PostMapping
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody QuizCreateReqDTO dto) {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(new QuizDTO(quizService.createQuiz(dto, userId)));
    }

    @PutMapping
    public ResponseEntity<QuizDTO> updateQuiz(@Valid @RequestBody QuizUpdateReqDTO dto) {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(new QuizDTO(quizService.updateQuiz(dto, userId)));
    }

    @PostMapping("/start")
    public ResponseEntity<QuizDTO> startQuiz(@Valid @RequestBody QuizStartDTO dto) {
        // quizService.startQuiz(dto);
        QuizDTO quizDTO = new QuizDTO(quizService.findQuizById(dto.getQuizId()));
        System.out.println("Quiz id: " + quizDTO.getId());
        socketIOConfig.getServer().getRoomOperations(String.valueOf(dto.getQuizId()))
                .sendEvent(SocketAction.START_QUIZ.getName(), quizDTO.getQuestions().getFirst());
        System.out.println("Мы отправили всем участниками об начлале игры");
        return ResponseEntity.ok(quizDTO);
    }

    @PostMapping("/finish")
    public ResponseEntity<String> finishQuiz(@PathVariable(name = "id") long quizId) {
        quizService.finishQuiz(quizId);
        return ResponseEntity.ok("The quiz has over");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuizById(@PathVariable(name = "id") long quizId) {
        quizService.deleteQuizById(quizId);
        return ResponseEntity.ok("The quiz was successfully deleted");
    }
}
