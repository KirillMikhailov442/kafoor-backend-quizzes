package kafoor.quizzes.quizzes_service.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OptionCreateReqDTO {
    @NotNull(message = "slug is mandatory")
    private String slug;

    @NotNull(message = "question slug is mandatory")
    private String questionSlug;

    private String text;

    @NotNull(message = "correct is mandatory")
    private boolean correct;
}
