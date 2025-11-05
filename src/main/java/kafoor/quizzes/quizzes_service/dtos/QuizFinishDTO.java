package kafoor.quizzes.quizzes_service.dtos;

import java.util.List;
import java.util.Map;

import kafoor.quizzes.quizzes_service.models.interfaces.UserAnswer;
import lombok.Data;

@Data
public class QuizFinishDTO {
    private long quizId;
    private Map<Long, List<UserAnswer>> answers;
}
