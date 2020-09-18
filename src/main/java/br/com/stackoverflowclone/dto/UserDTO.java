package br.com.stackoverflowclone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserDTO {
    @NotEmpty
    private String name;
    @Email
    private String email;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
