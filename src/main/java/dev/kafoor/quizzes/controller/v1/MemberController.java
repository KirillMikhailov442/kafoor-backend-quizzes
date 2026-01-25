package dev.kafoor.quizzes.controller.v1;

import dev.kafoor.quizzes.dto.v1.request.MemberCreateRequest;
import dev.kafoor.quizzes.dto.v1.response.ErrorResponse;
import dev.kafoor.quizzes.dto.v1.response.MemberResponse;
import dev.kafoor.quizzes.entity.MemberEntity;
import dev.kafoor.quizzes.entity.QuizEntity;
import dev.kafoor.quizzes.mapper.MemberMapper;
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
@RequestMapping("/api/v1/members")
@Tag(name = "Members", description = "Manage quiz participants (members): retrieve personal memberships and add members to quizzes")
public class MemberController {

    private final MemberService memberService;
    private final QuizService quizService;
    private final MemberMapper memberMapper;

    @Operation(summary = "Get a member by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid member ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Member not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getOne(
            @Parameter(description = "Member ID", required = true, example = "9")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id
    ) {
        return ResponseEntity.ok(memberMapper.toMemberResponse(memberService.findMemberByIdOrThrow(id)));
    }

    @Operation(summary = "Get all quiz memberships for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of memberships retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized – user not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/my")
    public ResponseEntity<List<MemberResponse>> getMyMembers() {
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        List<MemberEntity> memberEntities = memberService.findAllMembersByUserId(userId);
        List<MemberResponse> memberResponses = memberEntities
                .stream()
                .map(memberMapper::toMemberResponse)
                .toList();
        return ResponseEntity.ok(memberResponses);
    }

    @Operation(summary = "Add a participant (member) to a quiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participant added successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Validation failed (e.g., missing fields)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden – current user is not the quiz owner",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Quiz or target user not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<String> addMemberToQuiz(@Valid @RequestBody MemberCreateRequest request) {
        QuizEntity quizEntity = quizService.findQuizByIdOrThrow(request.getQuizId());
        memberService.addMember(quizEntity, request.getMemberId());
        return ResponseEntity.ok("the participant has been successfully added");
    }
}