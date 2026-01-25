package dev.kafoor.quizzes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members",
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_member_user_quiz",
                        columnNames = {"user_id", "quiz_id"})
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MemberEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private long id;

        @Column(name = "user_id", nullable = false)
        private long userId;

        @ManyToOne
        @JoinColumn(name = "quiz_id", nullable = false)
        private QuizEntity quiz;

        @CreationTimestamp
        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
        private List<MemberAnswerEntity> memberAnswers = new ArrayList<>();
}
