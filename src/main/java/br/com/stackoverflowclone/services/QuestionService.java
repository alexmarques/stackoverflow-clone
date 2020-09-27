package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.request.QuestionCreate;
import br.com.stackoverflowclone.request.QuestionUpdate;
import br.com.stackoverflowclone.response.QuestionResponseDTO;

import java.util.List;

public interface QuestionService {

    List<QuestionResponseDTO> findAll();
    QuestionResponseDTO save(QuestionCreate questionCreate);
    QuestionResponseDTO update(Long id, QuestionUpdate questionUpdate);
    QuestionResponseDTO delete(Long id);
    Question findById(Long id);
}
