package br.com.stackoverflowclone.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerVoteResponseDTO {
    private Long questionId;
    private Long answerId;
    private Long userId;
    private Integer score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
