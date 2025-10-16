package kafoor.quizzes.quizzes_service.dtos;

import kafoor.quizzes.quizzes_service.models.Question;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
public class QuestionDTO {
    private String text;
    private int scores;
    private List<QuestionOptionResDTO> options;
    private byte timeLimit;
    private UUID slug;

    public QuestionDTO(Question question) {
        text = question.getText();
        scores = question.getScores();
        timeLimit = question.getTimelimit();
        slug = question.getSlug();
        options = Optional.ofNullable(question.getOptions())
                .orElseGet(List::of)
                .stream()
                .map(QuestionOptionResDTO::new)
                .toList();
    }
}
