package dev.kafoor.quizzes.dto.v1.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OptionUpdate {
    private String slug;
    private String questionSlug;
    private String text;
    private boolean correct;
}
