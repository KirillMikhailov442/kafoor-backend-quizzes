package dev.kafoor.quizzes.dto.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsOptionResponse {
    private long id;
    private String slug;
    private String text;
    private String img;
    private boolean correct;
}
