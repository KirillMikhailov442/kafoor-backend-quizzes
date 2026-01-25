package dev.kafoor.quizzes.dto.v1.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUpdate {
    private String slug;
    private String text;
    private int scores;
    private byte timeLimit;
    private long quizId;
}
