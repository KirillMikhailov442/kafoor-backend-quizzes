package dev.kafoor.quizzes.dto.v1.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuizStartRequest {
    @NotNull(message = " id is mandatory")
    @Positive(message = "id must be a positive number")
    private long quizId;

    private List<Long> users;
}
