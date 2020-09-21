package br.com.stackoverflowclone.resources;

import br.com.stackoverflowclone.request.AnswerCreate;
import br.com.stackoverflowclone.request.QuestionCreate;
import br.com.stackoverflowclone.request.QuestionUpdate;
import br.com.stackoverflowclone.response.AnswerResponseDTO;
import br.com.stackoverflowclone.response.QuestionResponseDTO;
import br.com.stackoverflowclone.services.AnswerService;
import br.com.stackoverflowclone.services.QuestionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionResource {

    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionResource(QuestionService questionService,
                            AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping("/questions")
    public QuestionResponseDTO createQuestion(@RequestBody @Validated QuestionCreate question) {
        return this.questionService.save(question);
    }

    @GetMapping("/questions")
    public List<QuestionResponseDTO> findAllQuestions() {
        return this.questionService.findAll();
    }

    @PutMapping("/questions/{id}")
    public QuestionResponseDTO updateQuestion(@PathVariable Long id, @RequestBody @Validated QuestionUpdate question) {
        return this.questionService.update(id, question);
    }

    @DeleteMapping("/questions/{id}")
    public QuestionResponseDTO deleteQuestion(@PathVariable Long id) {
        return this.questionService.delete(id);
    }

    @GetMapping("/questions/{questionId}/answers")
    public List<AnswerResponseDTO> findAllAnswers(Long questionId) {
        return this.answerService.findAllByQuestionId(questionId);
    }

    @PostMapping("/questions/{questionId}/answers")
    public AnswerResponseDTO createAnswer(@RequestBody @Validated AnswerCreate answer) {
        return this.answerService.create(answer);
    }
}
