package kafoor.quizzes.quizzes_service.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuizStartDTO {
    private UUID quizId;
    private List<Long> users;
}
