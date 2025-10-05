package kafoor.quizzes.quizzes_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class QuizStartDTO {
    private long quizId;
    private List<Long> users;
}
