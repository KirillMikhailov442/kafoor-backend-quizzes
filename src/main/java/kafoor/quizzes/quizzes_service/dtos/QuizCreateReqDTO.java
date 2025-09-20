package kafoor.quizzes.quizzes_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kafoor.quizzes.quizzes_service.controllers.MemberConstants;

public class QuizCreateReqDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "maximum number of participants is mandatory")
    @Size(min = MemberConstants.MIN_COUNT_MEMBERS, max = MemberConstants.MAX_COUNT_MEMBERS, message = "the number of participants can vary from" + MemberConstants.MIN_COUNT_MEMBERS + " to " + MemberConstants.MAX_COUNT_MEMBERS)
    private int maxMember;
    
    private long userId;
}
