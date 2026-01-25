package dev.kafoor.quizzes.controller.v1;

import dev.kafoor.quizzes.dto.v1.request.QuestionCreateRequest;
import dev.kafoor.quizzes.dto.v1.request.QuestionUpdateRequest;
import dev.kafoor.quizzes.dto.v1.response.ErrorResponse;
import dev.kafoor.quizzes.dto.v1.response.QuestionResponse;
import dev.kafoor.quizzes.entity.QuestionEntity;
import dev.kafoor.quizzes.mapper.QuestionMapper;
import dev.kafoor.quizzes.service.QuestionService;
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
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1/questions")
@Tag(name = "Questions", description = "Manage quiz questions: create, read, update, and delete")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @Operation(summary = "Get all questions belonging to a specific quiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questions retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid quiz ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Quiz not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/by-quiz/{quizId}")
    public ResponseEntity<List<QuestionResponse>> getAllQuestionOfQuiz(
            @Parameter(description = "ID of the quiz", required = true, example = "5")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long quizId
    ) {
        List<QuestionResponse> questionResponses = questionService.findAllQuestionsOfQuiz(quizId)
                .stream()
                .map(questionMapper::toQuestionResponse)
                .toList();
        return ResponseEntity.ok(questionResponses);
    }

    @Operation(summary = "Get a question by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid question ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getOne(
            @Parameter(description = "Question ID", required = true, example = "12")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id
    ) {
        QuestionEntity questionEntity = questionService.findQuestionByIdOrThrow(id);
        return ResponseEntity.ok(questionMapper.toQuestionResponse(questionEntity));
    }

    @Operation(summary = "Create a new question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Question created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed (e.g., missing fields, invalid data)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden – user not allowed to create question for this quiz",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody QuestionCreateRequest request) {
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        QuestionEntity newQuestion = questionService.createQuestion(questionMapper.toQuestionCreate(request), userId);
        return new ResponseEntity<>(questionMapper.toQuestionResponse(newQuestion), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden – user not owner of the question",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping
    public ResponseEntity<QuestionResponse> updateQuestion(@Valid @RequestBody QuestionUpdateRequest request) {
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        QuestionEntity updatedQuestion = questionService.updateQuestion(questionMapper.toQuestionUpdate(request), userId);
        return ResponseEntity.ok(questionMapper.toQuestionResponse(updatedQuestion));
    }

    @Operation(summary = "Delete a question by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question deleted successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid question ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden – user not owner of the question",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestionById(
            @Parameter(description = "Question ID", required = true, example = "7")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id
    ) {
        questionService.deleteQuestionById(id);
        return ResponseEntity.ok("the question was successfully deleted");
    }

    @Operation(summary = "Delete a question by slug")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question deleted successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid slug format (must be 1–50 characters)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Question with given slug not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden – user not owner of the question",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("slug/{slug}")
    public ResponseEntity<String> deleteQuestionBySlug(
            @Parameter(description = "Unique slug identifier of the question", required = true, example = "what-is-java")
            @PathVariable
            @Size(min = 1, max = 50, message = "slug must be 1–50 characters long")
            String slug
    ) {
        questionService.deleteQuestionBySlug(slug);
        return ResponseEntity.ok("the question was successfully deleted");
    }
}