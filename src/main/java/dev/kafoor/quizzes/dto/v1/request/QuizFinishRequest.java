package dev.kafoor.quizzes.dto.v1.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class QuizFinishRequest {
    @NotNull(message = " id is mandatory")
    @Positive(message = "id must be a positive number")
    private long quizId;

    private Map<Long, List<UserAnswerRequest>> answers;
}
