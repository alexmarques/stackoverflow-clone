package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.exceptions.QuestionNotFoundException;
import br.com.stackoverflowclone.model.Flag;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.QuestionRepository;
import br.com.stackoverflowclone.repositories.operations.question.QuestionCreate;
import br.com.stackoverflowclone.repositories.operations.question.QuestionUpdate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final FlagService flagSerivce;

    public QuestionService(QuestionRepository questionRepository,
                           UserService userService,
                           FlagService flagSerivce) {
        this.questionRepository = questionRepository;
        this.userService = userService;
        this.flagSerivce = flagSerivce;
    }

    public List<Question> findAll() {
        return this.questionRepository.findAll();
    }

    public Question save(QuestionCreate questionCreate) {
        Question question = new Question();
        User user = this.userService.findById(questionCreate.getUserId());
        question.setUser(user);
        question.setFlags(questionCreate.getFlags());
        question.setResolved(Boolean.FALSE);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        question.setComment(questionCreate.getComment());
        return this.questionRepository.save(question);
    }

    public Question update(Long id, QuestionUpdate questionUpdate) {
        Question question = this.questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        question.setComment(questionUpdate.getComment());
        question.setUpdatedAt(LocalDateTime.now());
        questionUpdate.getFlags().stream()
                .forEach(description -> {
                    Flag flag = this.flagSerivce.insert(description);
                    if(question.getFlags() == null || question.getFlags().isEmpty()) {
                        question.setFlags(Collections.EMPTY_LIST);
                    }
                    question.getFlags().add(flag);
                });
        return this.questionRepository.save(question);
    }
}
