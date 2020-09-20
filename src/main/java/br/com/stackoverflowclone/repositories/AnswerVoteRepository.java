package br.com.stackoverflowclone.repositories;

import br.com.stackoverflowclone.model.AnswerVote;
import br.com.stackoverflowclone.model.ids.AnswerVoteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, AnswerVoteId> {

    List<AnswerVote> findAllByIdUserAndIdAnswer(Long userId, Long answerId);

}
