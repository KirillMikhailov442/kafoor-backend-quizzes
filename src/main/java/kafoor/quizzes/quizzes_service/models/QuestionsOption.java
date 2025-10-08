package kafoor.quizzes.quizzes_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "questions_option")
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsOption {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(unique = true, updatable = false)
    private UUID id;

    @JsonIgnore
    @OneToMany(mappedBy = "answer", fetch = FetchType.LAZY)
    private List<MemberAnswer> memberAnswers = new ArrayList<>();

    @JsonIgnore
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
