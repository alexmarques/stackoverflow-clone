package br.com.stackoverflowclone.converter;

import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.response.QuestionResponseDTO;

public class QuestionConverter {

    public static QuestionResponseDTO convert(Question question) {
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setUserId(question.getUser().getId());
        questionResponseDTO.setQuestionId(question.getId());
        question.getFlags().forEach(flag -> {
            questionResponseDTO.getFlags().add(flag.getDescription());
        });
        questionResponseDTO.setComment(question.getComment());
        questionResponseDTO.setCreatedAt(question.getCreatedAt());
        questionResponseDTO.setUpdatedAt(question.getUpdatedAt());
        questionResponseDTO.setResolved(question.getResolved());
        return questionResponseDTO;
    }
}
