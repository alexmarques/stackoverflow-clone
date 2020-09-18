package br.com.stackoverflowclone.model;

import br.com.stackoverflowclone.model.ids.QuestionId;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Question {

    @EmbeddedId
    private QuestionId id;
    private String comment;
    private Boolean resolved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
