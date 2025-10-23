package kafoor.quizzes.quizzes_service.dtos;

import kafoor.quizzes.quizzes_service.models.QuestionsOption;
import lombok.Data;

@Data
public class QuestionOptionResDTO {
    private String slug;
    private String text;
    private String img;
    private boolean isCorrect;

    public QuestionOptionResDTO(QuestionsOption questionsOption) {
        slug = questionsOption.getOption().getSlug();
        text = questionsOption.getOption().getText();
        img = questionsOption.getOption().getImg();
        isCorrect = questionsOption.isCorrect();
    }
}
