package kafoor.quizzes.quizzes_service.configs;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import kafoor.quizzes.quizzes_service.exceptions.Conflict;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.services.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SocketIOConfig {
    @Value("${socket.host}")
    private String host;

    @Value("${socket.port}")
    private int port;

    @Autowired
    private QuizService quizService;

    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer(){
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(port);
        config.setOrigin("*");

        server = new SocketIOServer(config);

        server.addConnectListener(client -> System.out.println("Client connected: " + client.getSessionId()));
        server.addDisconnectListener(client -> System.out.println("Client disconnected: " + client.getSessionId()));
        server.addEventListener("JOIN_QUIZ", Map.class, (client, data, ackSender) -> {
            System.out.println(data.get("quizId"));
        });
        server.start();
        return server;
    }

    @PreDestroy
    public void stopServer() {
        if (server != null) {
            server.stop();
        }
    }

}
