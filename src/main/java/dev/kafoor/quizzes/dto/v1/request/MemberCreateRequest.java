package dev.kafoor.quizzes.dto.v1.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {
    @NotNull(message = "quiz id is mandatory")
    @Positive(message = "quiz id must be a positive number")
    private Long quizId;

    @NotNull(message = "member id is mandatory")
    @Positive(message = "member id must be a positive number")
    private Long memberId;
}
