package dev.kafoor.quizzes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions",
        indexes = {
                @Index(name = "idx_question_slug", columnList = "slug")
        })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String slug;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionsOptionEntity> options = new ArrayList<>();

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
    private QuizEntity quiz;
}
