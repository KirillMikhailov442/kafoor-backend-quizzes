package kafoor.quizzes.quizzes_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OptionCreateReqDTO {
    @NotNull(message = "Question ID is mandatory")
    @Min(1)
    private long questionId;

    @NotBlank(message = "Text is mandatory")
    private String text;

    @NotNull(message = "isCorrect is mandatory")
    private boolean isCorrect;
}
