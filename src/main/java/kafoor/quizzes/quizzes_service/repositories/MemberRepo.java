package kafoor.quizzes.quizzes_service.repositories;

import kafoor.quizzes.quizzes_service.models.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {
    Member findFirstByUserId(long userId);

    List<Member> findAllByUserId(long userId);
}
