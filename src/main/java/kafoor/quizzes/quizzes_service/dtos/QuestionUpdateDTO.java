package kafoor.quizzes.quizzes_service.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kafoor.quizzes.quizzes_service.constants.QuestionConstants;
import lombok.Getter;

@Getter
public class QuestionUpdateDTO {
    @NotNull(message = " ID is mandatory")
    @Min(value = 1, message = "must be more than 0")
    private long id;

    @NotBlank(message = "Text is mandatory")
    private String text;

    @NotNull(message = "maximum number of participants is mandatory")
    @Min(value = 1, message = "must be more than 0")
    @Max(value = QuestionConstants.MAX_SCORES, message = "must be less than " + QuestionConstants.MAX_SCORES)
    private int scores;
}
