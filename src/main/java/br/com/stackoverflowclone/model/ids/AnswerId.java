package br.com.stackoverflowclone.model.ids;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class AnswerId implements Serializable {
    private Long id;
    private Long userId;
    private Long questionId;
}
