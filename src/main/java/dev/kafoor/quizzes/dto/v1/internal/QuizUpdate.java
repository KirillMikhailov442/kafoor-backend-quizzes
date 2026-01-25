package dev.kafoor.quizzes.dto.v1.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizUpdate {
    private long id;
    private String name;
    private int maxMembers;
}
