package kafoor.quizzes.quizzes_service.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kafoor.quizzes.quizzes_service.constants.QuestionConstants;
import lombok.Getter;

@Getter
public class QuestionCreateReqDTO {
    @NotNull(message = "slug is mandatory")
    private String slug;

    private String text;

    @NotNull(message = "maximum number of participants is mandatory")
    @Min(value = 1, message = "must be more than 0")
    @Max(value = QuestionConstants.MAX_SCORES, message = "must be less than " + QuestionConstants.MAX_SCORES)
    private int scores;

    @NotNull(message = "time limit of participants is mandatory")
    @Min(value = QuestionConstants.MIN_TIME_LIMIT, message = "must be more than " + QuestionConstants.MIN_TIME_LIMIT)
    @Max(value = QuestionConstants.MAX_TIME_LIMIT, message = "must be less than " + QuestionConstants.MAX_TIME_LIMIT)
    private byte timeLimit;

    @NotNull(message = "quiz ID is mandatory")
    private long quizId;
}
