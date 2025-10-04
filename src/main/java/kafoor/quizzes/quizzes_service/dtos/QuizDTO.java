package kafoor.quizzes.quizzes_service.dtos;

import kafoor.quizzes.quizzes_service.models.Quiz;
import lombok.Data;

import java.util.List;

@Data
public class QuizDTO {
    private String name;
    private int maxMember;
    private List<QuestionDTO> questions;
    private long endedAt;

    public QuizDTO(Quiz quiz){
        name = quiz.getName();
        maxMember = quiz.getMaxMember();
        questions = quiz.getQuestions().stream()
                .map(QuestionDTO::new)
                .toList();
        endedAt = quiz.getEndedAt();
    }
}
