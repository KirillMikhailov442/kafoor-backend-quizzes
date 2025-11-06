package kafoor.quizzes.quizzes_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Transient
    private Long quizId;

    @Transient
    private String quizName;

    @CreationTimestamp
    @Column(name = "created_at")
    private long createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberAnswer> memberAnswers = new ArrayList<>();

    public Long getQuizId() {
        return quiz != null ? quiz.getId() : null;
    }

    public String getQuizName() {
        return quiz != null ? quiz.getName() : null;
    }
}
