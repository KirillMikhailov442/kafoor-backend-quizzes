package dev.kafoor.quizzes.dto.v1.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizFinish {
    private long quizId;
    private Map<Long, List<UserAnswer>> answers;
}
