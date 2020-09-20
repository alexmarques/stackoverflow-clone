package br.com.stackoverflowclone.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;
    @ManyToOne
    private Question question;
    @OneToMany(orphanRemoval = true)
    private List<AnswerVote> votes;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
