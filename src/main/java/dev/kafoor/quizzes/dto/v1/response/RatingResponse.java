package dev.kafoor.quizzes.dto.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private Long id;
    private String name;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private List<MemberResponse> members;
    private List<QuestionResponse> questions;
    private List<MemberAnswerResponse> answers;
}
