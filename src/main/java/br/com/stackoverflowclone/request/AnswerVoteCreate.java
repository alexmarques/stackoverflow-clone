package br.com.stackoverflowclone.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerVoteCreate {
    @NotNull
    private Long userId;
    @NotNull
    private Integer score;
}