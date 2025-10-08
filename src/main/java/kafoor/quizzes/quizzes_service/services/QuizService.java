package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.dtos.QuizCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.QuizStartDTO;
import kafoor.quizzes.quizzes_service.dtos.QuizUpdateReqDTO;
import kafoor.quizzes.quizzes_service.exceptions.Conflict;
import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.repositories.MemberRepo;
import kafoor.quizzes.quizzes_service.repositories.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class QuizService {
    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private MemberService memberService;

    public List<Quiz> findAllQuizzesOfUser(long userId) {
        return quizRepo.findByUserId(userId);
    }

    public Quiz findQuizById(UUID id) {
        return quizRepo.findById(id).orElseThrow(() -> new NotFound("Quiz not found"));
    }

    public Quiz createQuiz(QuizCreateReqDTO dto, long userId) {
        Quiz newQuiz = Quiz.builder().name(dto.getName())
                .maxMembers(dto.getMaxMembers())
                .userId(userId).build();
        return quizRepo.save(newQuiz);
    }

    public Quiz updateQuiz(QuizUpdateReqDTO dto, long userId) {
        Quiz quiz = findQuizById(dto.getId());
        if (quiz.getUserId() != userId)
            throw new Conflict("This quiz does not belong to you");
        if (dto.getName() != null)
            quiz.setName(dto.getName());
        quiz.setMaxMembers(dto.getMaxMembers());
        return quizRepo.save(quiz);
    }

    public void deleteQuizById(UUID id) {
        if (quizRepo.existsById(id))
            throw new NotFound("Quiz not found");
        quizRepo.deleteById(id);
    }

    public void startQuiz(QuizStartDTO dto) {
        Quiz quiz = findQuizById(dto.getQuizId());
        quiz.setStartedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        memberService.addMembers(quiz, dto.getUsers());
    }

    public void finishQuiz(UUID id) {
        Quiz quiz = findQuizById(id);
        quiz.setEndedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
