package br.com.stackoverflowclone.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    private Question question;
    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "id.answer")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<AnswerVote> votes;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
