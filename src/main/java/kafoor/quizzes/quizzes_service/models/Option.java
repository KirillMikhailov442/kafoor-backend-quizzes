package kafoor.quizzes.quizzes_service.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "option", fetch = FetchType.LAZY)
    private List<QuestionsOption> questions = new ArrayList<>();

    @Column(name = "text", length = 64, nullable = false)
    private String text;

    @Column(name = "img")
    private String img;
}
