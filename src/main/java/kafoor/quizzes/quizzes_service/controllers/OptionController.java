package kafoor.quizzes.quizzes_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafoor.quizzes.quizzes_service.dtos.OptionCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.OptionUpdateReqDTO;
import kafoor.quizzes.quizzes_service.models.Option;
import kafoor.quizzes.quizzes_service.services.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Option", description = "Official option API")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/api/v1")
public class OptionController {
    @Autowired
    private OptionService optionService;

    @GetMapping("/options-of-question/{id}")
    public ResponseEntity<List<Option>> getAllOptionsOfQuestion(@PathVariable(name = "id") UUID questionId) {
        return ResponseEntity.ok(optionService.findAllOptionsOfQuestion(questionId));
    }

    @GetMapping("/options/{id}")
    public ResponseEntity<Option> getOneOption(@PathVariable(name = "id") UUID optionId) {
        return ResponseEntity.ok(optionService.findOptionById(optionId));
    }

    @PostMapping("/options")
    public ResponseEntity<Option> addOption(@Valid @RequestBody OptionCreateReqDTO dto) {
        return ResponseEntity.ok(optionService.addOptionToQuestion(dto));
    }

    @PutMapping("/options")
    public ResponseEntity<Option> updateOption(@Valid @RequestBody OptionUpdateReqDTO dto) {
        return ResponseEntity.ok(optionService.updateOption(dto));
    }

    @DeleteMapping("/options/{id}")
    public ResponseEntity<String> deleteOptionById(@PathVariable(name = "id") UUID optionId) {
        optionService.deleteOptionById(optionId);
        return ResponseEntity.ok("The option was successfully deleted");
    }
}
