package br.com.stackoverflowclone.repositories;

import br.com.stackoverflowclone.model.AnswerVote;
import br.com.stackoverflowclone.model.ids.AnswerVoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, AnswerVoteId> {

    @Query("from AnswerVote av where av.id.user.id = :userId and av.id.question.id = :questionId")
    List<AnswerVote> findAllByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);


}
