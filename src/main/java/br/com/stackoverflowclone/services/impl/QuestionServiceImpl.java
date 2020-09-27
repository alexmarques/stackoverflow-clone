package br.com.stackoverflowclone.services.impl;

import br.com.stackoverflowclone.converter.QuestionConverter;
import br.com.stackoverflowclone.exceptions.QuestionNotFoundException;
import br.com.stackoverflowclone.model.Flag;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.QuestionRepository;
import br.com.stackoverflowclone.request.QuestionCreate;
import br.com.stackoverflowclone.request.QuestionUpdate;
import br.com.stackoverflowclone.response.QuestionResponseDTO;
import br.com.stackoverflowclone.services.FlagService;
import br.com.stackoverflowclone.services.QuestionService;
import br.com.stackoverflowclone.services.UserReputationService;
import br.com.stackoverflowclone.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final FlagService flagSerivce;
    private final UserReputationService userReputationService;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                           UserService userService,
                           FlagService flagSerivce,
                           UserReputationService userReputationService) {
        this.questionRepository = questionRepository;
        this.userService = userService;
        this.flagSerivce = flagSerivce;
        this.userReputationService = userReputationService;
    }

    @Override
    public List<QuestionResponseDTO> findAll() {
        return this.questionRepository.findAll()
                .stream()
                .map(QuestionConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionResponseDTO save(QuestionCreate questionCreate) {
        Question question = new Question();
        User user = this.userService.findById(questionCreate.getUserId());
        question.setUser(user);
        questionCreate.getFlags().forEach(description -> {
            Flag flag = this.flagSerivce.insert(description);
            question.getFlags().add(flag);
        });
        question.setResolved(Boolean.FALSE);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        question.setComment(questionCreate.getComment());
        Question questionSaved = this.questionRepository.save(question);
        return QuestionConverter.convert(questionSaved);
    }

    @Override
    public QuestionResponseDTO update(Long id, QuestionUpdate questionUpdate) {
        Question question = this.questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        question.setComment(questionUpdate.getComment());
        question.setUpdatedAt(LocalDateTime.now());
        questionUpdate.getFlags().forEach(description -> {
            Flag flag = this.flagSerivce.insert(description);
            question.getFlags().add(flag);
        });
        Question questionSaved = this.questionRepository.save(question);
        return QuestionConverter.convert(questionSaved);
    }

    @Transactional
    @Override
    public QuestionResponseDTO delete(Long id) {
        Question question = this.questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        this.userReputationService.recalcularScore(question);
        this.questionRepository.delete(question);
        return QuestionConverter.convert(question);
    }

    @Override
    public Question findById(Long id) {
        return this.questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }
}
