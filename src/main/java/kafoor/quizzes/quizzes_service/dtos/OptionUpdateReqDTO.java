package kafoor.quizzes.quizzes_service.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OptionUpdateReqDTO {
    @NotNull(message = "slug is mandatory")
    private String slug;

    @NotNull(message = "question slug is mandatory")
    private String questionSlug;

    private String text;

    @NotNull(message = "is correct is mandatory")
    private boolean isCorrect;
}
