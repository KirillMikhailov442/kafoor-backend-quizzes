package kafoor.quizzes.quizzes_service.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OptionUpdateReqDTO {
    @NotNull(message = " ID is mandatory")
    private UUID id;

    @NotBlank(message = "Text is mandatory")
    private String text;

}
