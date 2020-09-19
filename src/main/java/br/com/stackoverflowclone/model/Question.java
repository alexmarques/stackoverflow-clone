package br.com.stackoverflowclone.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Flag> flags;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Answer> answers;
    @NotBlank
    private String comment;
    private Boolean resolved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
