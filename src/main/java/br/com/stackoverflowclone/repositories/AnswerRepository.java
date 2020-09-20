package br.com.stackoverflowclone.repositories;

import br.com.stackoverflowclone.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
