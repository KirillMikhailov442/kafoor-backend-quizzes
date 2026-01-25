package dev.kafoor.quizzes.dto.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAnswerRequest {
    @NotNull(message = "quiz id is mandatory")
    @Positive(message = "quiz id must be a positive number")
    private Long questionId;

    @NotBlank(message = "nickname is mandatory")
    private String nickname;

    private Long answer;
}
