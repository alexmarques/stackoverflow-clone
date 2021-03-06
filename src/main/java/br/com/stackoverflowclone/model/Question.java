package br.com.stackoverflowclone.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User user;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Flag> flags = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "question")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Answer> answers;
    @NotBlank
    private String comment;
    private Boolean resolved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
