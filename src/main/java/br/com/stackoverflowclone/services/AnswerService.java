package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.AnswerRepository;
import br.com.stackoverflowclone.repositories.operations.answer.AnswerCreate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserService userService;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository,
                         UserService userService,
                         QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.userService = userService;
        this.questionService = questionService;
    }

    public Answer create(AnswerCreate answerCreate) {
        User user = this.userService.findById(answerCreate.getUserId());
        Question question = this.questionService.findById(answerCreate.getQuestionId());
        Answer answer = new Answer();
        answer.setUser(user);
        answer.setQuestion(question);
        answer.setComment(answerCreate.getComment());
        answer.setCreatedAt(LocalDateTime.now());
        answer.setUpdatedAt(LocalDateTime.now());
        return this.answerRepository.save(answer);
    }
}
