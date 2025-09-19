package kafoor.quizzes.quizzes_service.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions_option")
public class QuestionsOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "answer", fetch = FetchType.LAZY)
    private List<MemberAnswer> memberAnswers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @CreationTimestamp
    @Column(name = "created_at")
    private long createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private long updatedAt;
}
