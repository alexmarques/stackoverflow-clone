package br.com.stackoverflowclone.exceptions;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException {
    private final Long userId;
    public UserNotFoundException(Long userId) {
        this.userId = userId;
    }
}
