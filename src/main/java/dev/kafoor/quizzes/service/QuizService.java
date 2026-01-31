package dev.kafoor.quizzes.service;

import dev.kafoor.quizzes.dto.v1.internal.QuizCreate;
import dev.kafoor.quizzes.dto.v1.internal.QuizStart;
import dev.kafoor.quizzes.dto.v1.internal.QuizUpdate;
import dev.kafoor.quizzes.entity.QuizEntity;
import dev.kafoor.quizzes.exception.Conflict;
import dev.kafoor.quizzes.exception.NotFound;
import dev.kafoor.quizzes.repository.QuizRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizRepo quizRepo;
    private final MemberService memberService;

    public List<QuizEntity> findAllQuizzesOfUser(long userId) {
        return quizRepo.findByUserId(userId);
    }

    public Optional<QuizEntity> findQuizById(long id) {
        return quizRepo.findById(id);
    }

    public QuizEntity findQuizByIdOrThrow(long id) {
        return quizRepo.findById(id).orElseThrow(() -> new NotFound("quiz not found by id"));
    }

    public boolean existsQuizById(long id) {
        return quizRepo.existsById(id);
    }

    public boolean isQuizOwnedByUser(long quizId, long userId) {
        QuizEntity quiz = findQuizByIdOrThrow(quizId);
        return quiz.getUserId() == userId;
    }

    public void isQuizOwnedByUserOrThrow(long quizId, long userId) {
        QuizEntity quiz = findQuizByIdOrThrow(quizId);
        if (quiz.getUserId() != userId) {
            throw new Conflict("this quiz does not belong to you");
        }
    }

    public QuizEntity createQuiz(QuizCreate dto, long userId) {
        QuizEntity newQuiz = QuizEntity.builder()
                .name(dto.getName())
                .maxMembers(dto.getMaxMembers())
                .userId(userId)
                .build();
        return quizRepo.save(newQuiz);
    }

    public QuizEntity updateQuiz(QuizUpdate dto, long userId) {
        QuizEntity quiz = findQuizByIdOrThrow(dto.getId());
        if (quiz.getUserId() != userId) {
            throw new Conflict("this quiz does not belong to you");
        }
        if (dto.getName() != null) {
            quiz.setName(dto.getName());
        }
        quiz.setMaxMembers(dto.getMaxMembers());
        return quizRepo.save(quiz);
    }

    public void deleteQuizById(long id) {
        if (!existsQuizById(id))
            throw new NotFound("quiz not found by id");
        quizRepo.deleteById(id);
    }

    public void startQuiz(QuizStart dto) {
        QuizEntity quiz = findQuizByIdOrThrow(dto.getQuizId());
        quiz.setStartedAt(LocalDateTime.now());
        quizRepo.save(quiz);

        List<Long> members = dto.getUsers()
                .stream()
                .filter(el -> !Objects.equals(el, quiz.getUserId()))
                .toList();
        memberService.addMembers(quiz, members);
    }

    public void finishQuiz(long id) {
        QuizEntity quiz = findQuizByIdOrThrow(id);
        quiz.setEndedAt(LocalDateTime.now());
        quizRepo.save(quiz);
    }
}
