package kafoor.quizzes.quizzes_service.models.interfaces;

import lombok.Getter;

@Getter
public class UserAnswer {
    private long questionId;
    private String nickname;
    private long answer;
}