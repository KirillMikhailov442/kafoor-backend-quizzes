package kafoor.quizzes.quizzes_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OptionUpdateReqDTO {
    @NotNull(message = " ID is mandatory")
    @Min(value = 1, message = "must be more than 0")
    private long id;

    @NotBlank(message = "Text is mandatory")
    private String text;

}
