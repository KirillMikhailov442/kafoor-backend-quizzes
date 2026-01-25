package dev.kafoor.quizzes.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kafoor.quizzes.dto.v1.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle( HttpServletRequest request,
                        HttpServletResponse response,
                        AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("you do not have sufficient permissions to perform this action")
                .path(request.getRequestURI())
                .timestamp(new Date())
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}