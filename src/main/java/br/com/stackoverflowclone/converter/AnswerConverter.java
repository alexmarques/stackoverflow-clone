package br.com.stackoverflowclone.converter;

import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.response.AnswerResponseDTO;

public class AnswerConverter {

    public static AnswerResponseDTO convert(Answer answer) {
        AnswerResponseDTO answerResponseDTO = new AnswerResponseDTO();
        answerResponseDTO.setAnswerId(answer.getId());
        answerResponseDTO.setUserId(answer.getUser().getId());
        answerResponseDTO.setQuestionId(answer.getQuestion().getId());
        answerResponseDTO.setComment(answer.getComment());
        answerResponseDTO.setCreatedAt(answer.getCreatedAt());
        answerResponseDTO.setUpdatedAt(answer.getUpdatedAt());
        return answerResponseDTO;
    }
}
