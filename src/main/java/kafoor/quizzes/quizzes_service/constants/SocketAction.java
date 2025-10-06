package kafoor.quizzes.quizzes_service.constants;

import lombok.Getter;

@Getter
public enum SocketAction {
    JOIN_TO_QUIZ("JOIN_TO_QUIZ"),
    LEAVE_FROM_QUIZ("LEAVE_FROM_QUIZ"),
    TELL_ABOUT_YOURSELF("TELL_ABOUT_YOURSELF"),
    START_QUIZ("START_QUIZ"),
    FINISH_QUIZ("FINISH_QUIZ"),
    NEXT_QUESTION("NEXT_QUESTION");

    private final String name;

    SocketAction(String name){
        this.name = name;
    }
}
