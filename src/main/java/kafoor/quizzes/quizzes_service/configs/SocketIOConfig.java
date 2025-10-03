package kafoor.quizzes.quizzes_service.configs;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import kafoor.quizzes.quizzes_service.exceptions.Conflict;
import kafoor.quizzes.quizzes_service.models.Quiz;
import kafoor.quizzes.quizzes_service.services.QuizService;
import kafoor.quizzes.quizzes_service.services.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
    public SocketIOServer socketIOServer(){
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(port);
        config.setOrigin("*");

        server = new SocketIOServer(config);

        server.addConnectListener(client -> {
            System.out.println("Client connected");
            System.out.println(client.getHandshakeData().getHttpHeaders().get("token"));
        });
        server.addDisconnectListener(client -> System.out.println("Client disconnected: " + client.getSessionId()));
        server.addEventListener("JOIN_TO_QUIZ", Map.class, (client, data, ackSender) -> {
            System.out.println("join to quiz");
            System.out.println(data.get("quizId"));
            client.joinRoom(data.get("quizId").toString());
            System.out.println("REDIS:");
            System.out.println(redisService.getValue(client.getSessionId().toString()));
            if(redisService.getValue(client.getSessionId().toString()) == null){
                System.out.println("Сохраняем socket id и client id в RAM");
                redisService.setValue(client.getSessionId().toString(), data.get("userId"));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("socketId", client.getSessionId());
            map.put("name", data.get("name"));
            map.put("nickname", data.get("nickname"));
            server.getRoomOperations(data.get("quizId").toString()).sendEvent("TELL_ME_YOURSELF", map );
        });
        server.addEventListener("LEAVE_FROM_QUIZ", String.class, (client, data, ackRequest) -> {
            System.out.println("LEAVE");
            System.out.println(data);
            client.leaveRoom(data);
            server.getRoomOperations(data).sendEvent("LEAVE_FROM_QUIZ", client.getSessionId().toString());
            redisService.deleteValue(client.getSessionId().toString());

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
