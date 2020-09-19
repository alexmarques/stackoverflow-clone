package br.com.stackoverflowclone.model.ids;

import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
public class AnswerVoteId implements Serializable {
    @OneToOne
    private Question question;
    @OneToOne
    private Answer answer;
    @OneToOne
    private User user;
}
