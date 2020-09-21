package br.com.stackoverflowclone.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class QuestionUpdate {
    @NotNull
    private Long userId;
    @NotBlank
    private String comment;
    @Size(min = 1, max = 20)
    private List<String> flags;
    private Boolean resolved;
}
