package kafoor.quizzes.quizzes_service.configs;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import kafoor.quizzes.quizzes_service.constants.SocketAction;
import kafoor.quizzes.quizzes_service.dtos.QuizStartDTO;
import kafoor.quizzes.quizzes_service.exceptions.Conflict;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.services.QuizService;
import kafoor.quizzes.quizzes_service.services.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
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

    @Autowired
    private RedisService redisService;

    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setOrigin("*");

        server = new SocketIOServer(config);

        server.addConnectListener(client -> {
            System.out.println("Client connected");
        });

        server.addDisconnectListener(client -> System.out.println("Client disconnected: " + client.getSessionId()));

        server.addEventListener(SocketAction.JOIN_TO_QUIZ.toString(), Map.class, (client, data, ackSender) -> {
            client.joinRoom(data.get("quizId").toString());
            // if (redisService.getValue(client.getSessionId().toString()) == null) {
            // System.out.println("Сохраняем socket id и client id в RAM");
            // redisService.setValue(client.getSessionId().toString(), data.get("userId"));
            // }
            Map<String, Object> map = new HashMap<>();
            map.put("socketId", client.getSessionId());
            map.put("name", data.get("name"));
            map.put("userId", data.get("userId"));
            map.put("nickname", data.get("nickname"));
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.JOIN_TO_QUIZ.getName(),
                    map);
        });

        server.addEventListener(SocketAction.TELL_ABOUT_YOURSELF.getName(), Map.class, (client, data, ackSender) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("socketId", client.getSessionId());
            map.put("name", data.get("name"));
            map.put("userId", data.get("userId"));
            map.put("nickname", data.get("nickname"));
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(
                    SocketAction.TELL_ABOUT_YOURSELF.getName(),
                    map);
        });

        server.addEventListener(SocketAction.LEAVE_FROM_QUIZ.getName(), Map.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.LEAVE_FROM_QUIZ.getName(),
                    client.getSessionId().toString());
            client.leaveRoom(data.get("quizId").toString());
            // redisService.deleteValue(client.getSessionId().toString());
        });

        server.addEventListener(SocketAction.NEXT_QUESTION.getName(), String.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data).sendEvent(SocketAction.NEXT_QUESTION.getName());
        });

        server.addEventListener(SocketAction.FINISH_QUIZ.toString(), String.class, (client, data, ackRequest) -> {

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
