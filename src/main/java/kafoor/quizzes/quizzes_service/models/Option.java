package kafoor.quizzes.quizzes_service.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "option", fetch = FetchType.LAZY)
    private List<QuestionsOption> questions = new ArrayList<>();

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "img")
    private String img;
}
