package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.request.AnswerCreate;
import br.com.stackoverflowclone.response.AnswerResponseDTO;

import java.util.List;

public interface AnswerService {
    AnswerResponseDTO create(AnswerCreate answerCreate);
    List<AnswerResponseDTO> findAllByQuestionId(Long questionId);
    Answer findById(Long answerId);
}
