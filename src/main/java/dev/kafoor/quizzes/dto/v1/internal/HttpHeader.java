package dev.kafoor.quizzes.dto.v1.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HttpHeader {
    public final String ip;
    public final String userAgent;

    @Override
    public String toString(){
        return "HttpHeader:" +
                "\n\tip:        " + ip +
                "\n\tuserAgent: " + userAgent;
    }
}