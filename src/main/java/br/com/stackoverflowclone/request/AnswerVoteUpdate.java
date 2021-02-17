package br.com.stackoverflowclone.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerVoteUpdate implements AnswerVoteOperation {
    @NotNull
    private Long userId;
    @NotNull
    private Integer score;
}
