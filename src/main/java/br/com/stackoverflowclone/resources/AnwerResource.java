package br.com.stackoverflowclone.resources;

import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.repositories.operations.answer.AnswerCreate;
import br.com.stackoverflowclone.services.AnswerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions/answers")
public class AnwerResource {

    private final AnswerService answerService;

    public AnwerResource(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public Answer create(@RequestBody @Validated AnswerCreate answer) {
        return this.answerService.create(answer);
    }

}
