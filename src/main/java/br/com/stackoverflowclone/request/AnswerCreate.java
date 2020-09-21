package br.com.stackoverflowclone.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AnswerCreate {
    @NotNull
    private Long userId;
    @NotNull
    private Long questionId;
    @NotBlank
    private String comment;
}
