package kafoor.quizzes.quizzes_service.dtos;

import java.util.UUID;

import kafoor.quizzes.quizzes_service.models.QuestionsOption;
import lombok.Data;

@Data
public class QuestionOptionResDTO {
    private UUID id;
    private String text;
    private String img;
    private boolean isCorrect;

    public QuestionOptionResDTO(QuestionsOption questionsOption) {
        id = questionsOption.getId();
        text = questionsOption.getOption().getText();
        img = questionsOption.getOption().getImg();
        isCorrect = questionsOption.isCorrect();
    }
}
