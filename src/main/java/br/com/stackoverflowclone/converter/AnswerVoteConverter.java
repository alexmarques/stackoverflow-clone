package br.com.stackoverflowclone.converter;

import br.com.stackoverflowclone.model.AnswerVote;
import br.com.stackoverflowclone.response.AnswerVoteResponseDTO;

public class AnswerVoteConverter {

    public static AnswerVoteResponseDTO convert(AnswerVote answerVote) {
        AnswerVoteResponseDTO answerVoteResponseDTO = new AnswerVoteResponseDTO();
        answerVoteResponseDTO.setAnswerId(answerVote.getId().getAnswer().getId());
        answerVoteResponseDTO.setQuestionId(answerVote.getId().getQuestion().getId());
        answerVoteResponseDTO.setUserId(answerVote.getId().getUser().getId());
        answerVoteResponseDTO.setScore(answerVote.getScore());
        answerVoteResponseDTO.setCreatedAt(answerVote.getCreatedAt());
        answerVoteResponseDTO.setUpdatedAt(answerVote.getUpdatedAt());
        return answerVoteResponseDTO;
    }


}
