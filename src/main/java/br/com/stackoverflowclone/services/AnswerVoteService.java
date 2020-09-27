package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.AnswerVote;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.request.AnswerVoteCreate;
import br.com.stackoverflowclone.request.AnswerVoteUpdate;
import br.com.stackoverflowclone.response.AnswerVoteResponseDTO;

import java.util.List;

public interface AnswerVoteService {
    List<AnswerVote> findBy(Long questionId, Long userId);
    AnswerVoteResponseDTO createVoteAnswer(Question question, Answer answer, AnswerVoteCreate answerVoteCreate);
    AnswerVoteResponseDTO updateVoteAnswer(Question question, Answer answer, AnswerVoteUpdate answerVoteUpdate);
}
