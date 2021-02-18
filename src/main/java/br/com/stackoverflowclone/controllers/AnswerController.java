package br.com.stackoverflowclone.controllers;

import br.com.stackoverflowclone.request.AnswerCreate;
import br.com.stackoverflowclone.response.AnswerResponseDTO;
import br.com.stackoverflowclone.services.AnswerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions/{questionId}")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/answers")
    public List<AnswerResponseDTO> findAllAnswers(Long questionId) {
        return this.answerService.findAllByQuestionId(questionId);
    }

    @PostMapping("/answers")
    public AnswerResponseDTO createAnswer(@RequestBody @Validated AnswerCreate answer) {
        return this.answerService.create(answer);
    }
}
