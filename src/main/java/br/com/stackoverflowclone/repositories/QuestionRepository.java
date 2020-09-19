package br.com.stackoverflowclone.repositories;

import br.com.stackoverflowclone.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
