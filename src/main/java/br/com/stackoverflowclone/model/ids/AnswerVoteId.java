package br.com.stackoverflowclone.model.ids;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class AnswerVoteId implements Serializable {
    private Long questionId;
    private Long answerId;
    private Long userId;
}
