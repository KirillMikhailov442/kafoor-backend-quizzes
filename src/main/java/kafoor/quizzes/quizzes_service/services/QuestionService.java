package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.dtos.QuestionCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.QuestionUpdateDTO;
import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.models.Question;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.repositories.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;
    private QuizService quizService;

    public List<Question> findAllQuestionsOfQuiz(long quizId){
        Quiz quiz = quizService.findQuizById(quizId);
        return questionRepo.findAllByQuiz(quiz);
    }

    public Question findQuestionById(long id){
        return questionRepo.findById(id).orElseThrow(() -> new NotFound("Question not found"));
    }

    public Question createQuestion(QuestionCreateReqDTO dto){
        Question newQuestion = Question.builder()
                .text(dto.getText())
                .scores(dto.getScores())
                .build();
        return questionRepo.save(newQuestion);
    }

    public Question updateQuestion(QuestionUpdateDTO dto){
        Question question = findQuestionById(dto.getId());
        if(dto.getText().isBlank()) question.setText(dto.getText());
        question.setScores(dto.getScores());
        return questionRepo.save(question);
    }

    public void deleteQuestionById(long id){
        if(questionRepo.existsById(id)) throw new NotFound("Question not found");
        questionRepo.deleteById(id);
    }
}
