package br.com.stackoverflowclone.model;

import br.com.stackoverflowclone.model.ids.AnswerId;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Answer {

    @EmbeddedId
    private AnswerId id;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
