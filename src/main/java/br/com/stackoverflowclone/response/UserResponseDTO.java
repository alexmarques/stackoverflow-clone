package br.com.stackoverflowclone.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
    private Boolean enabled;
}
