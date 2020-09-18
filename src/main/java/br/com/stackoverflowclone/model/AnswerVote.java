package br.com.stackoverflowclone.model;

import br.com.stackoverflowclone.model.ids.AnswerVoteId;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Data
public class AnswerVote {

    @EmbeddedId
    private AnswerVoteId id;
    private Integer score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
