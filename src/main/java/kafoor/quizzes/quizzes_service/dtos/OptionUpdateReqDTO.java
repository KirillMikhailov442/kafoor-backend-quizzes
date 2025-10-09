package kafoor.quizzes.quizzes_service.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OptionUpdateReqDTO {
    @NotBlank(message = " ID is mandatory")
    private long id;

    @NotBlank(message = "Text is mandatory")
    private String text;

    @NotBlank(message = "client ID is mandatory")
    private UUID clientId;

}
