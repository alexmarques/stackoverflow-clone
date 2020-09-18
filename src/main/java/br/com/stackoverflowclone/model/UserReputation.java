package br.com.stackoverflowclone.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class UserReputation {

    @Id
    private Long id;
    private Integer score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

}
