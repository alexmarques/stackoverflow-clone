package br.com.stackoverflowclone.resources;

import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.request.*;
import br.com.stackoverflowclone.response.AnswerResponseDTO;
import br.com.stackoverflowclone.response.AnswerVoteResponseDTO;
import br.com.stackoverflowclone.response.QuestionResponseDTO;
import br.com.stackoverflowclone.services.AnswerService;
import br.com.stackoverflowclone.services.AnswerVoteService;
import br.com.stackoverflowclone.services.QuestionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionResource {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AnswerVoteService answerVoteService;

    public QuestionResource(QuestionService questionService,
                            AnswerService answerService,
                            AnswerVoteService answerVoteService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.answerVoteService = answerVoteService;
    }

    @PostMapping
    public QuestionResponseDTO createQuestion(@RequestBody @Validated QuestionCreate question) {
        return this.questionService.save(question);
    }

    @GetMapping
    public List<QuestionResponseDTO> findAllQuestions() {
        return this.questionService.findAll();
    }

    @PutMapping("/{id}")
    public QuestionResponseDTO updateQuestion(@PathVariable Long id, @RequestBody @Validated QuestionUpdate question) {
        return this.questionService.update(id, question);
    }

    @DeleteMapping("/{id}")
    public QuestionResponseDTO deleteQuestion(@PathVariable Long id) {
        return this.questionService.delete(id);
    }

    @GetMapping("/{questionId}/answers")
    public List<AnswerResponseDTO> findAllAnswers(Long questionId) {
        return this.answerService.findAllByQuestionId(questionId);
    }

    @PostMapping("/{questionId}/answers")
    public AnswerResponseDTO createAnswer(@RequestBody @Validated AnswerCreate answer) {
        return this.answerService.create(answer);
    }

    @PostMapping("/{questionId}/answers/{answerId}/vote")
    public AnswerVoteResponseDTO createVoteAnswer(@PathVariable Long questionId,
                                                  @PathVariable Long answerId,
                                                  @Validated @RequestBody AnswerVoteCreate answerVoteCreate) {
        Question question = this.questionService.findById(questionId);
        Answer answer = this.answerService.findById(answerId);
        return this.answerVoteService.createVoteAnswer(question, answer, answerVoteCreate);
    }

    @PutMapping("/{questionId}/answers/{answerId}/vote")
    public AnswerVoteResponseDTO updateVoteAnswer(@PathVariable Long questionId,
                                                  @PathVariable Long answerId,
                                                  @Validated @RequestBody AnswerVoteUpdate answerVoteUpdate) {
        Question question = this.questionService.findById(questionId);
        Answer answer = this.answerService.findById(answerId);
        return this.answerVoteService.updateVoteAnswer(question, answer, answerVoteUpdate);
    }
}