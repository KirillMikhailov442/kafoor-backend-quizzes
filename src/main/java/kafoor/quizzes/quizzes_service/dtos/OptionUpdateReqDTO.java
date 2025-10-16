package kafoor.quizzes.quizzes_service.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OptionUpdateReqDTO {
    @NotBlank(message = "slug is mandatory")
    private UUID slug;

    @NotBlank(message = "Text is mandatory")
    private String text;

}
