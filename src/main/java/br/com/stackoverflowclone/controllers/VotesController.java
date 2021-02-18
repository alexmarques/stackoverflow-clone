package br.com.stackoverflowclone.controllers;

import br.com.stackoverflowclone.request.AnswerVoteOperationCreate;
import br.com.stackoverflowclone.request.AnswerVoteUpdate;
import br.com.stackoverflowclone.response.AnswerVoteResponseDTO;
import br.com.stackoverflowclone.services.AnswerVoteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions/{questionId}/answers/{answerId}")
public class VotesController {

    private final AnswerVoteService answerVoteService;

    public VotesController(AnswerVoteService answerVoteService) {
        this.answerVoteService = answerVoteService;
    }

    @PostMapping("/vote")
    public AnswerVoteResponseDTO createVoteAnswer(@PathVariable Long questionId,
                                                  @PathVariable Long answerId,
                                                  @Validated @RequestBody AnswerVoteOperationCreate answerVoteCreate) {
        return this.answerVoteService.createVoteAnswer(questionId, answerId, answerVoteCreate);
    }

    @PutMapping("/vote")
    public AnswerVoteResponseDTO updateVoteAnswer(@PathVariable Long questionId,
                                                  @PathVariable Long answerId,
                                                  @Validated @RequestBody AnswerVoteUpdate answerVoteUpdate) {
        return this.answerVoteService.updateVoteAnswer(questionId, answerId, answerVoteUpdate);
    }
}
