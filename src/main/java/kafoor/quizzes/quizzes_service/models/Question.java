package kafoor.quizzes.quizzes_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "questions")
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "client_id", unique = true)
    private UUID clientId;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionsOption> options = new ArrayList<>();

    @Column(name = "time_limit", nullable = false)
    private byte timelimit;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "img")
    private String img;

    @Column(name = "video")
    private String video;

    @Column(name = "scores", nullable = false)
    private int scores;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;
}
