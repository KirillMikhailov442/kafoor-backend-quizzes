package kafoor.quizzes.quizzes_service.dtos;

import kafoor.quizzes.quizzes_service.models.Member;
import kafoor.quizzes.quizzes_service.models.Quiz;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class QuizDTO {
    private long id;
    private String name;
    private int maxMember;
    private List<QuestionDTO> questions;
    private List<Member> members;
    private long endedAt;

    public QuizDTO(Quiz quiz){
        id = quiz.getId();
        name = quiz.getName();
        maxMember = quiz.getMaxMember();
        questions = Optional.ofNullable(quiz.getQuestions())
                .orElseGet(List::of)
                .stream()
                .map(QuestionDTO::new)
                .toList();
        members = quiz.getMembers();
        endedAt = quiz.getEndedAt();
    }
}
