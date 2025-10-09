package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.dtos.QuestionCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.QuestionUpdateDTO;
import kafoor.quizzes.quizzes_service.exceptions.Conflict;
import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.models.Question;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.repositories.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private QuizService quizService;

    public List<Question> findAllQuestionsOfQuiz(UUID quizId) {
        Quiz quiz = quizService.findQuizById(quizId);
        return questionRepo.findAllByQuiz(quiz);
    }

    public Question findQuestionById(UUID id) {
        return questionRepo.findById(id).orElseThrow(() -> new NotFound("Question not found"));
    }

    public Question createQuestion(QuestionCreateReqDTO dto, long userId) {
        Quiz quiz = quizService.findQuizById(dto.getQuizId());
        if (quiz.getUserId() != userId)
            throw new Conflict("This question does not belong to you");
        Question newQuestion = Question.builder()
                .text(dto.getText())
                .scores(dto.getScores())
                .timelimit(dto.getTimeLimit())
                .quiz(quiz)
                .id(dto.getQuestionId())
                .build();
        return questionRepo.save(newQuestion);
    }

    public Question updateQuestion(QuestionUpdateDTO dto, long userId) {
        Quiz quiz = quizService.findQuizById(dto.getQuizId());
        if (quiz.getUserId() != userId)
            throw new Conflict("This question does not belong to you");
        Question question = questionRepo.findById(dto.getId()).orElse(new Question());

        if (dto.getText().isBlank())
            question.setText(dto.getText());

        if (dto.getTimeLimit() != 0)
            question.setTimelimit((dto.getTimeLimit()));

        if (question.getId() == null)
            question.setId(dto.getId());

        question.setScores(dto.getScores());
        return questionRepo.save(question);
    }

    public void deleteQuestionById(UUID id) {
        if (questionRepo.existsById(id))
            throw new NotFound("Question not found");
        questionRepo.deleteById(id);
    }
}
