package kafoor.quizzes.quizzes_service.services;

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
}
