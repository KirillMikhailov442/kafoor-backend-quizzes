package dev.kafoor.quizzes.dto.v1.request;

import dev.kafoor.quizzes.constant.MemberConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizUpdateRequest {
        @NotNull(message = "id is mandatory")
        @Positive(message = "id must be a positive number")
        private long id;

        @NotNull(message = "name is not be null")
        private String name;

        @NotNull(message = "maximum number of participants is mandatory")
        @Min(value = MemberConstants.MIN_COUNT_MEMBERS, message = "must be more than "
                        + MemberConstants.MIN_COUNT_MEMBERS)
        @Max(value = MemberConstants.MAX_COUNT_MEMBERS, message = "must be less than "
                        + MemberConstants.MAX_COUNT_MEMBERS)
        private int maxMembers;
}
