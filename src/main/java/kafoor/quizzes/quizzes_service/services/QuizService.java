package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.dtos.QuizCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.QuizUpdateReqDTO;
import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.repositories.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepo quizRepo;

    public List<Quiz> findAllQuizzesOfUser(long userId){
        List<Quiz> quizzes = quizRepo.findByUserId(userId);
        if(quizzes.isEmpty()) throw new NotFound("Unable to find user quizzes");
        return quizzes;
    }

    public Quiz findQuizById(long id){
        return quizRepo.findById(id).orElseThrow(() -> new NotFound("Quiz not found"));
    }

    public Quiz createQuiz(QuizCreateReqDTO dto){
        Quiz newQuiz = Quiz.builder().name(dto.getName())
                .maxMember(dto.getMaxMember())
                .userId(dto.getUserId()).build();
        return quizRepo.save(newQuiz);
    }

    public Quiz updateQuiz(QuizUpdateReqDTO dto){
        Quiz quiz = findQuizById(dto.getId());
        if(dto.getName() != null) quiz.setName(dto.getName());
        quiz.setMaxMember(dto.getMaxMember());
        return quizRepo.save(quiz);
    }

    public void deleteQuizById(long id){
        if(quizRepo.existsById(id)) throw new NotFound("Quiz not found");
        quizRepo.deleteById(id);
    }
}
