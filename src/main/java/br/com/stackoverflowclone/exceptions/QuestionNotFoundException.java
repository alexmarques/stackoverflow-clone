package br.com.stackoverflowclone.exceptions;

import lombok.Data;

@Data
public class QuestionNotFoundException extends RuntimeException {
    private final Long questionId;
    public QuestionNotFoundException(Long questionId) {
        this.questionId = questionId;
    }
}
