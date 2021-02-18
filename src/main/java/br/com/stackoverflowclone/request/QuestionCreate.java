package br.com.stackoverflowclone.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreate {
    @NotNull
    private Long userId;
    @NotBlank
    private String comment;
    @NotNull
    private List<String> flags;
}
