package dev.kafoor.quizzes.repository;

import dev.kafoor.quizzes.entity.MemberAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAnswerRepo extends JpaRepository<MemberAnswerEntity, Long> {
    List<MemberAnswerEntity> findByMemberIdIn(List<Long> membersIds);
}
