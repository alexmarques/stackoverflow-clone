package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.AnswerVote;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.request.AnswerVoteOperationCreate;
import br.com.stackoverflowclone.request.AnswerVoteUpdate;
import br.com.stackoverflowclone.response.AnswerVoteResponseDTO;

import javax.transaction.Transactional;
import java.util.List;

public interface AnswerVoteService {
    List<AnswerVote> findBy(Long questionId, Long userId);
    @Transactional
    AnswerVoteResponseDTO createVoteAnswer(Long questionId, Long answerId, AnswerVoteOperationCreate answerVoteCreate);
    AnswerVoteResponseDTO updateVoteAnswer(Long questionId, Long answerId, AnswerVoteUpdate answerVoteUpdate);
}
