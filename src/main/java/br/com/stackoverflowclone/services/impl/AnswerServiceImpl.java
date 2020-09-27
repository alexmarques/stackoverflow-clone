package br.com.stackoverflowclone.services.impl;

import br.com.stackoverflowclone.converter.AnswerConverter;
import br.com.stackoverflowclone.exceptions.AnswerNotFoundException;
import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.AnswerRepository;
import br.com.stackoverflowclone.request.AnswerCreate;
import br.com.stackoverflowclone.response.AnswerResponseDTO;
import br.com.stackoverflowclone.services.AnswerService;
import br.com.stackoverflowclone.services.QuestionService;
import br.com.stackoverflowclone.services.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final UserService userService;
    private final QuestionService questionService;

    public AnswerServiceImpl(AnswerRepository answerRepository,
                         UserService userService,
                         QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.userService = userService;
        this.questionService = questionService;
    }

    @Override
    @Transactional
    public AnswerResponseDTO create(AnswerCreate answerCreate) {
        User user = this.userService.findById(answerCreate.getUserId());
        Question question = this.questionService.findById(answerCreate.getQuestionId());
        Answer answer = new Answer();
        answer.setUser(user);
        answer.setQuestion(question);
        answer.setComment(answerCreate.getComment());
        answer.setCreatedAt(LocalDateTime.now());
        answer.setUpdatedAt(LocalDateTime.now());
        Answer answerSaved = this.answerRepository.save(answer);
        return AnswerConverter.convert(answerSaved);
    }

    @Override
    public List<AnswerResponseDTO> findAllByQuestionId(Long questionId) {
        return this.answerRepository.findAllByQuestionId(questionId)
                .stream()
                .map(AnswerConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Answer findById(Long answerId) {
        return this.answerRepository.findById(answerId).orElseThrow(() -> new AnswerNotFoundException(answerId));
    }
}
