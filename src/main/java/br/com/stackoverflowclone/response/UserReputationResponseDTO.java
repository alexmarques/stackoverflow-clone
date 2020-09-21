package br.com.stackoverflowclone.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserReputationResponseDTO {
    private Long userId;
    private Long userReputationId;
    private LocalDateTime createdAt;
    private LocalDateTime updadetAt;
    private Integer score;

}
