package kafoor.quizzes.quizzes_service.dtos;

import jakarta.validation.constraints.*;
import kafoor.quizzes.quizzes_service.constants.MemberConstants;
import lombok.Getter;

@Getter
public class QuizUpdateReqDTO {
    @NotNull(message = " ID is mandatory")
    private long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotNull(message = "maximum number of participants is mandatory")
    @Min(value = MemberConstants.MIN_COUNT_MEMBERS, message = "must be more than " + MemberConstants.MIN_COUNT_MEMBERS)
    @Max(value = MemberConstants.MAX_COUNT_MEMBERS, message = "must be less than " + MemberConstants.MAX_COUNT_MEMBERS)
    private int maxMembers;
}
