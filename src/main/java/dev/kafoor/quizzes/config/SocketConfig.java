package dev.kafoor.quizzes.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import dev.kafoor.quizzes.constant.SocketAction;
import dev.kafoor.quizzes.service.QuizService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Getter
public class SocketConfig {
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
        config.setHostname(host);
        config.setPort(port);
        config.setOrigin("*");

        server = new SocketIOServer(config);

        server.addConnectListener(client -> System.out.println("client connected: " + client.getSessionId()));

        server.addDisconnectListener(client -> System.out.println("client disconnected: " + client.getSessionId()));

        server.addEventListener(SocketAction.JOIN_TO_QUIZ.name(), Map.class, (client, data, ackSender) -> {
            Map<String, Object> body = new HashMap<>();
            body.put("socketId", client.getSessionId());
            body.put("name", data.get("name"));
            body.put("nickname", data.get("nickname"));
            body.put("userId", data.get("userId"));
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.JOIN_TO_QUIZ.name(), body);
        });

        server.addEventListener(SocketAction.LEAVE_FROM_QUIZ.toString(), Map.class, (client, data, ackRequest) -> {
            server.getRoomOperations(data.get("quizId").toString()).sendEvent(SocketAction.LEAVE_FROM_QUIZ.toString(),
                    client.getSessionId().toString());
            client.leaveRoom(data.get("quizId").toString());
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

        server.addEventListener(SocketAction.EXPULSION_FROM_QUIZ.toString(), String.class, (client, data, ackRequest) -> {
                    server.getClient(UUID.fromString(data)).sendEvent(SocketAction.EXPULSION_FROM_QUIZ.toString());});

        server.start();
        return server;
    }
}
