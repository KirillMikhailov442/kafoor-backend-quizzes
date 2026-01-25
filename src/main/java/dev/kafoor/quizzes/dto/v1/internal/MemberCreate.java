package dev.kafoor.quizzes.dto.v1.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberCreate {
    private long quizId;
    private long memberId;
}
