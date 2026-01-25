package dev.kafoor.quizzes.controller.v1;

import dev.kafoor.quizzes.dto.v1.request.OptionCreateRequest;
import dev.kafoor.quizzes.dto.v1.request.OptionUpdateRequest;
import dev.kafoor.quizzes.dto.v1.response.ErrorResponse;
import dev.kafoor.quizzes.dto.v1.response.OptionResponse;
import dev.kafoor.quizzes.entity.OptionEntity;
import dev.kafoor.quizzes.mapper.OptionMapper;
import dev.kafoor.quizzes.service.OptionService;
import dev.kafoor.quizzes.service.QuestionsOptionService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/options")
@Tag(name = "Options", description = "Manage answer options for quiz questions: create, read, update, and delete")
public class OptionController {

    private final OptionService optionService;
    private final QuestionsOptionService questionsOptionService;
    private final OptionMapper optionMapper;

    @Operation(summary = "Get all options for a specific question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Options retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid question ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/by-question/{questionId}")
    public ResponseEntity<List<OptionResponse>> getAllOptionOfQuestion(
            @Parameter(description = "ID of the question", required = true, example = "8")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long questionId
    ) {
        List<OptionResponse> optionResponses = questionsOptionService.findAllOptionsOfQuestion(questionId)
                .stream()
                .map(optionMapper::toOptionResponse)
                .toList();
        return ResponseEntity.ok(optionResponses);
    }

    @Operation(summary = "Get an option by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Option found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid option ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Option not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OptionResponse> getOne(
            @Parameter(description = "Option ID", required = true, example = "22")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id
    ) {
        OptionEntity optionEntity = optionService.findOptionByIdOrThrow(id);
        return ResponseEntity.ok(optionMapper.toOptionResponse(optionEntity));
    }

    @Operation(summary = "Create a new option for a question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Option created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed (e.g., missing fields, invalid question ID)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden – user not owner of the associated question",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Parent question not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<OptionResponse> createOption(@Valid @RequestBody OptionCreateRequest request) {
        OptionEntity optionEntity = optionService.addOptionToQuestion(optionMapper.toOptionCreate(request));
        return ResponseEntity.ok(optionMapper.toOptionResponse(optionEntity));
    }

    @Operation(summary = "Update an existing option")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Option updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Option not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden – user not owner of the associated question",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping
    public ResponseEntity<OptionResponse> updateOption(@Valid @RequestBody OptionUpdateRequest request) {
        OptionEntity optionEntity = optionService.updateOption(optionMapper.toOptionUpdate(request));
        return ResponseEntity.ok(optionMapper.toOptionResponse(optionEntity));
    }

    @Operation(summary = "Delete an option by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Option deleted successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid option ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Option not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden – user not owner of the associated question",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOptionById(
            @Parameter(description = "Option ID", required = true, example = "15")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id
    ) {
        optionService.deleteOptionById(id);
        return ResponseEntity.ok("the option was successfully deleted");
    }

    @Operation(summary = "Delete an option by slug")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Option deleted successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid slug format (must be 1–50 characters)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Option with given slug not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden – user not owner of the associated question",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/slug/{slug}")
    public ResponseEntity<String> deleteOptionBySlug(
            @Parameter(description = "Unique slug identifier of the option", required = true, example = "option-java")
            @PathVariable
            @Size(min = 1, max = 50, message = "slug must be 1–50 characters long")
            String slug
    ) {
        optionService.deleteOptionBySlug(slug);
        return ResponseEntity.ok("the option was successfully deleted");
    }
}