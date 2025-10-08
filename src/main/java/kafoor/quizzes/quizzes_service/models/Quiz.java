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
@Table(name = "quizzes")
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(unique = true, updatable = false)
    private UUID id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "max_members", nullable = false)
    private int maxMembers;

    @JsonIgnore
    @Column(name = "user_id", nullable = false)
    private long userId;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private long createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private long updatedAt;

    @Column(name = "ended_at")
    private long endedAt;

    @Column(name = "started_at")
    private long startedAt;
}
