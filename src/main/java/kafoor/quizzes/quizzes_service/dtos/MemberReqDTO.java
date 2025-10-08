package kafoor.quizzes.quizzes_service.dtos;

import java.util.UUID;

import lombok.Getter;

@Getter
public class MemberReqDTO {
    private UUID quizId;
    private long memberId;
}
