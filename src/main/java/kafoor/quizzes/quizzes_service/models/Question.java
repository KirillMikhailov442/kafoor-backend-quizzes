package kafoor.quizzes.quizzes_service.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionsOption> options = new ArrayList<>();

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "img")
    private String img;

    @Column(name = "video")
    private String video;

    @Column(name = "scores", nullable = false)
    private int scores;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;
}
