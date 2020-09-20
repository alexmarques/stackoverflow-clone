package br.com.stackoverflowclone.resources;

import br.com.stackoverflowclone.dto.QuestionResponseDTO;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.repositories.operations.question.QuestionCreate;
import br.com.stackoverflowclone.repositories.operations.question.QuestionUpdate;
import br.com.stackoverflowclone.services.QuestionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionResource {

    private QuestionService questionService;

    public QuestionResource(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public QuestionResponseDTO insert(@RequestBody @Validated QuestionCreate question) {
        return this.questionService.save(question);
    }

    @GetMapping
    public List<Question> findAll() {
        return this.questionService.findAll();
    }

    @PutMapping("/{id}")
    public QuestionResponseDTO update(@PathVariable Long id, @RequestBody @Validated QuestionUpdate question) {
        return this.questionService.update(id, question);
    }

    @DeleteMapping("/{id}")
    public QuestionResponseDTO delete(@PathVariable Long id) {
        return this.questionService.delete(id);
    }
}
