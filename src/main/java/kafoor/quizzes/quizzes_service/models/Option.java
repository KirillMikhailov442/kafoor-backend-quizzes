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
@Table(name = "options")
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private UUID slug;

    @JsonIgnore
    @OneToMany(mappedBy = "option", fetch = FetchType.LAZY)
    private List<QuestionsOption> questions = new ArrayList<>();

    @Column(name = "text", length = 64, nullable = false)
    private String text;

    @Column(name = "img")
    private String img;
}
