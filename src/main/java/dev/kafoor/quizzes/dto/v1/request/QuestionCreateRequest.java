package dev.kafoor.quizzes.dto.v1.request;

import dev.kafoor.quizzes.constant.QuestionConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionCreateRequest {
        @NotBlank(message = "slug must not be blank")
        private String slug;

        @NotNull(message = "text must not be null")
        private String text;

        @NotNull(message = "maximum number of participants is mandatory")
        @Min(value = QuestionConstants.MIN_SCORES, message = "must be more than " + (QuestionConstants.MIN_SCORES - 1))
        @Max(value = QuestionConstants.MAX_SCORES, message = "must be less than " + QuestionConstants.MAX_SCORES)
        private Integer scores;

        @NotNull(message = "time limit of participants is mandatory")
        @Min(value = QuestionConstants.MIN_TIME_LIMIT, message = "must be more than "
                        + QuestionConstants.MIN_TIME_LIMIT)
        @Max(value = QuestionConstants.MAX_TIME_LIMIT, message = "must be less than "
                        + QuestionConstants.MAX_TIME_LIMIT)
        private Byte timeLimit;

        @NotNull(message = "quiz id is mandatory")
        @Positive(message = "quiz id must be a positive number")
        private Long quizId;
}
