package br.com.stackoverflowclone.repositories;

import br.com.stackoverflowclone.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
     List<Answer> findAllByQuestionId(Long questionId);
}
