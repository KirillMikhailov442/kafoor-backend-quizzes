package dev.kafoor.quizzes.repository;

import dev.kafoor.quizzes.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepo extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findFirstByUserId(long userId);

    List<MemberEntity> findAllByUserId(long userId);
}
