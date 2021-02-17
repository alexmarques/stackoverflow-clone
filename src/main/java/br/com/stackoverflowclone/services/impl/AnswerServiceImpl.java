package br.com.stackoverflowclone.services.impl;

import br.com.stackoverflowclone.converter.AnswerConverter;
import br.com.stackoverflowclone.exceptions.AnswerNotFoundException;
import br.com.stackoverflowclone.exceptions.QuestionNotFoundException;
import br.com.stackoverflowclone.exceptions.UserNotFoundException;
import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.AnswerRepository;
import br.com.stackoverflowclone.repositories.QuestionRepository;
import br.com.stackoverflowclone.repositories.UserRepository;
import br.com.stackoverflowclone.request.AnswerCreate;
import br.com.stackoverflowclone.response.AnswerResponseDTO;
import br.com.stackoverflowclone.services.AnswerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository,
                             UserRepository userRepository,
                             QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public AnswerResponseDTO create(AnswerCreate answerCreate) {

        User user = this.userRepository.findById(answerCreate.getUserId())
                .orElseThrow(() -> new UserNotFoundException(answerCreate.getUserId()));

        Question question = this.questionRepository.findById(answerCreate.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException(answerCreate.getQuestionId()));

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
