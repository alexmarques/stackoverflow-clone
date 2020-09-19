package br.com.stackoverflowclone.repositories.operations.question;

import br.com.stackoverflowclone.model.Flag;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuestionCreate {
    @NotNull
    private Long userId;
    @NotBlank
    private String comment;
    @Min(1)
    private List<Flag> flags;
}
