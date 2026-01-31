package dev.kafoor.quizzes.controller.v1;

import dev.kafoor.quizzes.config.SocketConfig;
import dev.kafoor.quizzes.constant.SocketAction;
import dev.kafoor.quizzes.dto.v1.request.QuizCreateRequest;
import dev.kafoor.quizzes.dto.v1.request.QuizFinishRequest;
import dev.kafoor.quizzes.dto.v1.request.QuizStartRequest;
import dev.kafoor.quizzes.dto.v1.request.QuizUpdateRequest;
import dev.kafoor.quizzes.dto.v1.response.*;
import dev.kafoor.quizzes.entity.MemberEntity;
import dev.kafoor.quizzes.entity.QuizEntity;
import dev.kafoor.quizzes.mapper.MemberAnswerMapper;
import dev.kafoor.quizzes.mapper.MemberMapper;
import dev.kafoor.quizzes.mapper.QuestionMapper;
import dev.kafoor.quizzes.mapper.QuizMapper;
import dev.kafoor.quizzes.service.MemberAnswerService;
import dev.kafoor.quizzes.service.MemberService;
import dev.kafoor.quizzes.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/quizzes")
@Tag(name = "Quizzes", description = "Manage quizzes: create, update, start, finish, and retrieve")
public class QuizController {
        private final QuizService quizService;
        private final MemberAnswerService memberAnswerService;
        private final MemberService memberService;
        private final QuizMapper quizMapper;
        private final QuestionMapper questionMapper;
        private final MemberMapper memberMapper;
        private final MemberAnswerMapper memberAnswerMapper;
        private final SocketConfig socket;

        @Operation(summary = "Get all quizzes created by the current user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of quizzes retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/mine")
        public ResponseEntity<List<QuizResponse>> getAllMyQuizzes() {
                long userId = Long.parseLong(Objects
                                .requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
                List<QuizEntity> quizEntities = quizService.findAllQuizzesOfUser(userId);
                List<QuizResponse> quizResponses = quizEntities
                                .stream()
                                .map(quizMapper::toQuizResponse)
                                .toList();
                return ResponseEntity.ok(quizResponses);
        }

        @Operation(summary = "Get a quiz by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quiz found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<QuizResponse> getOne(
                        @Parameter(description = "Quiz ID", required = true, example = "1") @PathVariable @Positive(message = "id must be a positive number") Long id) {
                QuizEntity quizEntity = quizService.findQuizByIdOrThrow(id);
                return ResponseEntity.ok(quizMapper.toQuizResponse(quizEntity));
        }

        @Operation(summary = "Get rating data for a quiz (questions, members, and answers)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Rating data retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid quiz ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/rating/{quizId}")
        public ResponseEntity<RatingResponse> getRating(
                        @Parameter(description = "Quiz ID", required = true, example = "1") @PathVariable @Positive(message = "id must be a positive number") Long quizId) {
                QuizEntity quizEntity = quizService.findQuizByIdOrThrow(quizId);
                List<Long> membersId = quizEntity.getMembers()
                                .stream()
                                .map(MemberEntity::getId)
                                .toList();

                List<QuestionResponse> questionResponses = quizEntity.getQuestions()
                                .stream()
                                .map(questionMapper::toQuestionResponse)
                                .toList();
                List<MemberResponse> memberResponses = quizEntity.getMembers()
                                .stream()
                                .map(memberMapper::toMemberResponse)
                                .toList();
                List<MemberAnswerResponse> memberAnswerResponses = memberAnswerService.findAnswersByListId(membersId)
                                .stream()
                                .map(memberAnswerMapper::toAnswerResponse)
                                .toList();

                RatingResponse response = RatingResponse.builder()
                                .id(quizEntity.getId())
                                .name(quizEntity.getName())
                                .startedAt(quizEntity.getStartedAt())
                                .endedAt(quizEntity.getEndedAt())
                                .questions(questionResponses)
                                .members(memberResponses)
                                .answers(memberAnswerResponses)
                                .build();

                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Create a new quiz")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quiz created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping
        public ResponseEntity<QuizResponse> createQuiz(@Valid @RequestBody QuizCreateRequest request) {
                long userId = Long.parseLong(Objects
                                .requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
                QuizEntity quizEntity = quizService.createQuiz(quizMapper.toQuizCreate(request), userId);
                return ResponseEntity.ok(quizMapper.toQuizResponse(quizEntity));
        }

        @Operation(summary = "Update an existing quiz")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quiz updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Validation failed or invalid data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PutMapping
        public ResponseEntity<QuizResponse> updateQuiz(@Valid @RequestBody QuizUpdateRequest request) {
                long userId = Long.parseLong(Objects
                                .requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
                QuizEntity quizEntity = quizService.updateQuiz(quizMapper.toQuizUpdate(request), userId);
                return ResponseEntity.ok(quizMapper.toQuizResponse(quizEntity));
        }

        @Operation(summary = "Get quizzes participated in by the current user", description = "Returns a list of all quizzes in which the authenticated user has participated (via membership records).")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of quizzes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))),
                        @ApiResponse(responseCode = "401", description = "User is not authenticated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "403", description = "Access denied (e.g., insufficient permissions)"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping("/me/participated")
        public ResponseEntity<List<QuizResponse>> getQuizzesMeParticipated() {
                long userId = Long.parseLong(Objects
                                .requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
                List<QuizResponse> quizResponses = memberService.findAllMembersByUserId(userId)
                                .stream()
                                .map(el -> quizMapper.toQuizResponse(el.getQuiz()))
                                .toList();
                return ResponseEntity.ok(quizResponses);
        }

        @Operation(summary = "Start a quiz")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quiz started successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "403", description = "Insufficient permissions to start the quiz", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping("/start")
        public ResponseEntity<QuizResponse> startQuiz(@Valid @RequestBody QuizStartRequest request) {
                quizService.startQuiz(quizMapper.toQuizStart(request));
                QuizResponse quizResponse = quizMapper
                                .toQuizResponse(quizService.findQuizByIdOrThrow(request.getQuizId()));
                socket.getServer().getRoomOperations(String.valueOf(request.getQuizId()))
                                .sendEvent(SocketAction.START_QUIZ.toString(), quizResponse.getQuestions().getFirst());
                return ResponseEntity.ok(quizResponse);
        }

        @Operation(summary = "Finish a quiz")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quiz finished successfully", content = @Content(mediaType = "text/plain")),
                        @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "403", description = "Insufficient permissions to finish the quiz", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping("/finish")
        public ResponseEntity<String> finishQuiz(@Valid @RequestBody QuizFinishRequest request) {
                quizService.finishQuiz(request.getQuizId());
                memberAnswerService.addAnswersMember(memberAnswerMapper.toQuizFinish(request));
                return ResponseEntity.ok("the quiz has over");
        }

        @Operation(summary = "Delete a quiz by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quiz deleted successfully", content = @Content(mediaType = "text/plain")),
                        @ApiResponse(responseCode = "400", description = "Invalid ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "403", description = "Insufficient permissions to delete the quiz", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteQuizById(
                        @Parameter(description = "Quiz ID", required = true, example = "1") @PathVariable @Positive(message = "id must be a positive number") Long id) {
                quizService.deleteQuizById(id);
                return ResponseEntity.ok("the quiz was successfully deleted");
        }
}