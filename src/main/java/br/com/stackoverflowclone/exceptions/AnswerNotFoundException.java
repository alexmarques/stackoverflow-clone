package br.com.stackoverflowclone.exceptions;

import lombok.Data;

@Data
public class AnswerNotFoundException extends RuntimeException {
    private final Long answerId;
    public AnswerNotFoundException(Long answerId) {
        this.answerId = answerId;
    }
}
