package dev.kafoor.quizzes.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Enter your JWT access token (e.g., Bearer ey...)"
)
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Quiz Platform API (MVP)")
                        .version("v1")
                        .description("""
                            MVP version of the quiz platform API.  
                            Supports:
                            - Quiz, question, and option management  
                            - Adding participants  
                            - Starting and finishing quizzes  
                            
                            All endpoints require authentication via JWT.
                            """)
                        .contact(new Contact()
                                .name("Mikhailov Kirill")
                                .email("akcjdjs123456789@gmail.com")
                                .url("https://github.com/KirillMikhailov442/kafoor-backend-quizzes")))
                .servers(List.of(
                        new Server().url("/").description("Auto-detected"),
                        new Server().url("http://localhost:8081").description("Local")
                ));
    }

    @Bean
    public GroupedOpenApi v1Api() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/v1/**")
                .build();
    }
}