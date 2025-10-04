package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.dtos.QuizCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.QuizUpdateReqDTO;
import kafoor.quizzes.quizzes_service.exceptions.Conflict;
import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.repositories.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepo quizRepo;

    public List<Quiz> findAllQuizzesOfUser(long userId){
        List<Quiz> quizzes = quizRepo.findByUserId(userId);
        System.out.println(quizzes.size());
        if(quizzes.isEmpty()) throw new NotFound("Unable to find user quizzes");
        return quizzes;
    }

    public Quiz findQuizById(long id){
        return quizRepo.findById(id).orElseThrow(() -> new NotFound("Quiz not found"));
    }

    public Quiz createQuiz(QuizCreateReqDTO dto, long userId){
        Quiz newQuiz = Quiz.builder().name(dto.getName())
                .maxMember(dto.getMaxMember())
                .userId(userId).build();
        return quizRepo.save(newQuiz);
    }

    public Quiz updateQuiz(QuizUpdateReqDTO dto, long userId){
        Quiz quiz = findQuizById(dto.getId());
        if(quiz.getUserId() != userId) throw new Conflict("This quiz does not belong to you");
        if(dto.getName() != null) quiz.setName(dto.getName());
        quiz.setMaxMember(dto.getMaxMember());
        return quizRepo.save(quiz);
    }

    public void deleteQuizById(long id){
        if(quizRepo.existsById(id)) throw new NotFound("Quiz not found");
        quizRepo.deleteById(id);
    }

    public void finishQuiz(long id){
        Quiz quiz = findQuizById(id);
        quiz.setEndedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
