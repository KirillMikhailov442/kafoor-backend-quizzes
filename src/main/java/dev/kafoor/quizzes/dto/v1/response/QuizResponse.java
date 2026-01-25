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
public class QuizResponse {
    private long id;
    private String name;
    private int maxMembers;
    private List<QuestionResponse> questions;
    private List<MemberResponse> members;
    private long startedAt;
    private long endedAt;
    private long createdAt;
    private long updatedAt;
    private long userId;
}
