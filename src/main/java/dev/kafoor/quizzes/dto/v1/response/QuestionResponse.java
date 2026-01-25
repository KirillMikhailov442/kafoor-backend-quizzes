package dev.kafoor.quizzes.dto.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private long id;
    private String text;
    private int scores;
    private List<QuestionsOptionResponse> options;
    private byte timeLimit;
    private String slug;
}
