package dev.kafoor.quizzes.dto.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionCreateRequest {
    @NotBlank(message = "slug must not be blank")
    private String slug;

    @NotBlank(message = "question slug must not be blank")
    private String questionSlug;

    @NotNull(message = "text must not be null")
    private String text;

    @NotNull(message = "correct is mandatory")
    private boolean correct;
}
