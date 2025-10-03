package kafoor.quizzes.quizzes_service.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")        // для всех путей
                .allowedOrigins("*")      // разрешить все домены
                .allowedMethods("*")      // разрешить все HTTP методы (GET, POST, и т.д.)
                .allowedHeaders("*");    // разрешить все заголовки
    }
}
