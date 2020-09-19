package br.com.stackoverflowclone.repositories.operations.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserUpdate {
    @NotBlank
    private String name;
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;
    private Boolean enabled;

}
