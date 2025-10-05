package kafoor.quizzes.quizzes_service.dtos;

import kafoor.quizzes.quizzes_service.models.Question;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class QuestionDTO {
    private long id;
    private String text;
    private int scores;
    private List<QuestionOptionResDTO> options;

    public QuestionDTO(Question question){
        id = question.getId();
        text = question.getText();
        scores = question.getScores();
        options = Optional.ofNullable(question.getOptions())
                .orElseGet(List::of)
                .stream()
                .map(QuestionOptionResDTO::new)
                .toList();
    }
}
