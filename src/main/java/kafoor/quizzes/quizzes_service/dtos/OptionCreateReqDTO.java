package kafoor.quizzes.quizzes_service.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OptionCreateReqDTO {
    @NotNull(message = "Question ID is mandatory")
    private UUID questionId;

    @NotBlank(message = "Option ID is mandatory")
    private UUID optionId;

    @NotBlank(message = "Text is mandatory")
    private String text;

    @NotNull(message = "isCorrect is mandatory")
    private boolean isCorrect;
}
