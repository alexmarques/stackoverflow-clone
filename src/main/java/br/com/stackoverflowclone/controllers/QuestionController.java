package br.com.stackoverflowclone.controllers;

import br.com.stackoverflowclone.request.QuestionCreate;
import br.com.stackoverflowclone.request.QuestionUpdate;
import br.com.stackoverflowclone.response.QuestionResponseDTO;
import br.com.stackoverflowclone.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createQuestion(@RequestBody @Validated QuestionCreate question) {
        QuestionResponseDTO questionResponseDTO = this.questionService.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionResponseDTO);
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


}