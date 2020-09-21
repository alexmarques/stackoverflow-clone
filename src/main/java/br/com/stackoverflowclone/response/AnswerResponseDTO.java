package br.com.stackoverflowclone.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerResponseDTO {
    private Long answerId;
    private Long questionId;
    private Long userId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
