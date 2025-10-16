package kafoor.quizzes.quizzes_service.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OptionCreateReqDTO {
    @NotBlank(message = "slug is mandatory")
    private UUID slug;

    @NotNull(message = "question ID is mandatory")
    private long questionId;

    @NotBlank(message = "text is mandatory")
    private String text;

    @NotNull(message = "is correct is mandatory")
    private boolean isCorrect;
}
