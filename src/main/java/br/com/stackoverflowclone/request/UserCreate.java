package br.com.stackoverflowclone.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserCreate {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;

}
