package kafoor.quizzes.quizzes_service.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OptionCreateReqDTO {
    @NotNull(message = "slug is mandatory")
    private UUID slug;

    @NotNull(message = "question slug is mandatory")
    private UUID questionSlug;

    private String text;

    @NotNull(message = "is correct is mandatory")
    private boolean isCorrect;
}
