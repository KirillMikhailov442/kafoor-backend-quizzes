package dev.kafoor.quizzes.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "member_answers",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_member_answer",
                        columnNames = {"member_id", "answer_id"}
                )
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MemberAnswerEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private long id;

        @ManyToOne
        @JoinColumn(name = "member_id", nullable = false)
        private MemberEntity member;

        @ManyToOne
        @JoinColumn(name = "answer_id", nullable = false)
        private QuestionsOptionEntity answer;
}
