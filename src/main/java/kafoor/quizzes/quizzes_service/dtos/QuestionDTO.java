package kafoor.quizzes.quizzes_service.dtos;

import kafoor.quizzes.quizzes_service.models.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private String text;
    private int scores;
    private List<QuestionOptionResDTO> options;

    public QuestionDTO(Question question){
        text = question.getText();
        scores = question.getScores();
        options = question.getOptions().stream()
                .map(QuestionOptionResDTO::new)
                .toList();
    }
}
