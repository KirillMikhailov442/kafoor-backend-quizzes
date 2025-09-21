package kafoor.quizzes.quizzes_service.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OptionCreateReqDTO {
    @NotBlank(message = "Text is mandatory")
    private String text;
}
