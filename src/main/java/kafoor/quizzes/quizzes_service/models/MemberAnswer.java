package kafoor.quizzes.quizzes_service.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Builder
@Entity
@Table(name = "members_answer")
public class MemberAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    private QuestionsOption answer;

    @CreationTimestamp
    @Column(name = "created_at")
    private long createdAt;
}
