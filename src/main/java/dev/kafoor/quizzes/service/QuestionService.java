package dev.kafoor.quizzes.service;

import dev.kafoor.quizzes.dto.v1.internal.QuestionCreate;
import dev.kafoor.quizzes.dto.v1.internal.QuestionUpdate;
import dev.kafoor.quizzes.entity.QuestionEntity;
import dev.kafoor.quizzes.entity.QuizEntity;
import dev.kafoor.quizzes.exception.NotFound;
import dev.kafoor.quizzes.repository.QuestionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepo questionRepo;
    private final QuizService quizService;

    public Optional<QuestionEntity> findQuestionById(long id){
        return questionRepo.findById(id);
    }

    public QuestionEntity findQuestionByIdOrThrow(long id){
        return questionRepo.findById(id).orElseThrow(() -> new NotFound("question not found by id"));
    }

    public Optional<QuestionEntity> findQuestionBySlug(String slug) {
        return questionRepo.findBySlug(slug);
    }

    public QuestionEntity findQuestionBySlugOrThrow(String slug) {
        return questionRepo.findBySlug(slug).orElseThrow(() -> new NotFound("question not found by slug"));
    }

    public List<QuestionEntity> findAllQuestionsOfQuiz(long quizId){
        QuizEntity quiz = quizService.findQuizByIdOrThrow(quizId);
        return quiz.getQuestions();
    }

    public boolean existsQuestionById(long id){
        return questionRepo.existsById(id);
    }

    public boolean existsQuestionBySlug(String slug){
        return questionRepo.existsBySlug(slug);
    }

    public QuestionEntity createQuestion(QuestionCreate dto, long userId){
        quizService.isQuizOwnedByUserOrThrow(dto.getQuizId(), userId);
        QuizEntity quiz = quizService.findQuizById(dto.getQuizId()).get();
        QuestionEntity newQuestion = QuestionEntity.builder()
                .text(dto.getText())
                .scores(dto.getScores())
                .slug(dto.getSlug())
                .timelimit(dto.getTimeLimit())
                .quiz(quiz)
                .build();
        return questionRepo.save(newQuestion);
    }

    public QuestionEntity updateQuestion(QuestionUpdate dto, long userId){
        quizService.isQuizOwnedByUserOrThrow(dto.getQuizId(), userId);
        QuizEntity quiz = quizService.findQuizByIdOrThrow(dto.getQuizId());
        QuestionEntity question = findQuestionBySlug(dto.getSlug()).orElse(new QuestionEntity());

        question.setText(dto.getText());
        question.setTimelimit((dto.getTimeLimit()));
        question.setQuiz(quiz);
        question.setSlug(dto.getSlug());
        question.setScores(dto.getScores());

        return questionRepo.save(question);
    }

    public void deleteQuestionById(long id) {
        if (!existsQuestionById(id))
            throw new NotFound("question not found by id");
        questionRepo.deleteById(id);
    }

    public void deleteQuestionBySlug(String slug) {
        if (!existsQuestionBySlug(slug))
            throw new NotFound("question not found by slug");
        questionRepo.deleteBySlug(slug);
    }
}
