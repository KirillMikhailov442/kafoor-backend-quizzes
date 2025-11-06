package kafoor.quizzes.quizzes_service.configs;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import kafoor.quizzes.quizzes_service.constants.SocketAction;
import kafoor.quizzes.quizzes_service.services.QuizService;
import kafoor.quizzes.quizzes_service.services.RedisService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@Getter
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
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.JOIN_TO_QUIZ.toString(),
                    map);
        });

        server.addEventListener(SocketAction.TELL_ABOUT_YOURSELF.toString(), Map.class, (client, data, ackSender) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("socketId", client.getSessionId());
            map.put("name", data.get("name"));
            map.put("userId", data.get("userId"));
            map.put("nickname", data.get("nickname"));
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(
                    SocketAction.TELL_ABOUT_YOURSELF.toString(),
                    map);
        });

        server.addEventListener(SocketAction.LEAVE_FROM_QUIZ.toString(), Map.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.LEAVE_FROM_QUIZ.toString(),
                    client.getSessionId().toString());
            client.leaveRoom(data.get("quizId").toString());
            redisService.deleteValue(client.getSessionId().toString());
        });

        server.addEventListener(SocketAction.TIMER.toString(), Map.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.TIMER.toString(),
                    data.get("timer"));
        });

        server.addEventListener(SocketAction.NEXT_QUESTION.toString(), Map.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.NEXT_QUESTION.toString(),
                    data.get("question"));
        });

        server.addEventListener(SocketAction.TELL_CORRECT_ANSWER.toString(), Map.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data.get("quizId").toString())
                    .sendEvent(SocketAction.TELL_CORRECT_ANSWER.toString(), data.get("corrects"));
        });

        server.addEventListener(SocketAction.SAY_MY_ANSWER.toString(), Map.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.SAY_MY_ANSWER.toString(),
                    data);
        });

        server.addEventListener(SocketAction.TELL_RATING.toString(), Map.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.TELL_RATING.toString(),
                    data.get("rating"));
        });

        server.addEventListener(SocketAction.FINISH_QUIZ.toString(), Map.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.FINISH_QUIZ.toString(),
                    data.get("rating"));
        });

        server.addEventListener(SocketAction.EXPULSION_FROM_QUIZ.toString(), String.class,
                (client, data, ackRequest) -> {
                    server.getClient(UUID.fromString(data))
                            .sendEvent(SocketAction.EXPULSION_FROM_QUIZ.toString());
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
