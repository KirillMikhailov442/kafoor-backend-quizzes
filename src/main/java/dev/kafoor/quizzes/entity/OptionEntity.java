package dev.kafoor.quizzes.entity;

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
@Table(name = "options",
        indexes = {
                @Index(name = "idx_option_slug", columnList = "slug")
        })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OptionEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private Long id;

        @Column(nullable = false, unique = true)
        private String slug;

        @OneToMany(mappedBy = "option", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        private List<QuestionsOptionEntity> questions = new ArrayList<>();

        @Column(name = "text", length = 64, nullable = false)
        private String text;

        @Column(name = "img")
        private String img;
}
