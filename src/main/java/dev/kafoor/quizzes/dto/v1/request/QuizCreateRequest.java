package dev.kafoor.quizzes.dto.v1.request;

import dev.kafoor.quizzes.constant.MemberConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizCreateRequest {
    @NotBlank(message = "name is mandatory")
    private String name;

    @NotNull(message = "maximum number of participants is mandatory")
    @Min(
            value = MemberConstants.MIN_COUNT_MEMBERS,
            message = "must be more than " + MemberConstants.MIN_COUNT_MEMBERS
    )
    @Max(
            value = MemberConstants.MAX_COUNT_MEMBERS,
            message = "must be less than " + MemberConstants.MAX_COUNT_MEMBERS
    )
    private int maxMembers;
}
