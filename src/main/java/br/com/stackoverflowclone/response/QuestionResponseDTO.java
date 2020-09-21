package br.com.stackoverflowclone.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionResponseDTO {
    private Long questionId;
    private Long userId;
    private List<String> flags = new ArrayList<>();
    private String comment;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
    private Boolean resolved;
}
