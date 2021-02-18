package br.com.stackoverflowclone.services.impl;

import br.com.stackoverflowclone.converter.AnswerVoteConverter;
import br.com.stackoverflowclone.exceptions.AnswerNotFoundException;
import br.com.stackoverflowclone.exceptions.QuestionNotFoundException;
import br.com.stackoverflowclone.exceptions.UserNotFoundException;
import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.AnswerVote;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.model.ids.AnswerVoteId;
import br.com.stackoverflowclone.repositories.AnswerRepository;
import br.com.stackoverflowclone.repositories.AnswerVoteRepository;
import br.com.stackoverflowclone.repositories.QuestionRepository;
import br.com.stackoverflowclone.repositories.UserRepository;
import br.com.stackoverflowclone.request.AnswerVoteOperation;
import br.com.stackoverflowclone.request.AnswerVoteOperationCreate;
import br.com.stackoverflowclone.request.AnswerVoteUpdate;
import br.com.stackoverflowclone.response.AnswerVoteResponseDTO;
import br.com.stackoverflowclone.services.AnswerVoteService;
import br.com.stackoverflowclone.services.CalculadorScoreService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerVoteServiceImpl implements AnswerVoteService {

    private final AnswerVoteRepository answerVoteRepository;
    private final UserRepository userRepository;
    private final CalculadorScoreService calculadorScoreService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AnswerVoteServiceImpl(AnswerVoteRepository answerVoteRepository,
                                 UserRepository userRepository,
                                 CalculadorScoreService calculadorScoreService, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.answerVoteRepository = answerVoteRepository;
        this.userRepository = userRepository;
        this.calculadorScoreService = calculadorScoreService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public List<AnswerVote> findBy(Long questionId, Long userId) {
        return this.answerVoteRepository.findAllByUserIdAndQuestionId(userId, questionId);
    }

    @Override
    @Transactional
    public AnswerVoteResponseDTO createVoteAnswer(Long questionId, Long answerId, AnswerVoteOperationCreate answerVoteCreate) {
        Question question = this.questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException(questionId));
        Answer answer = this.answerRepository.findById(answerId).orElseThrow(() -> new AnswerNotFoundException(answerId));
        return getAnswerVoteResponseDTO(question, answer, answerVoteCreate);
    }

    @Override
    public AnswerVoteResponseDTO updateVoteAnswer(Long questionId, Long answerId, AnswerVoteUpdate answerVoteUpdate) {
        Question question = this.questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException(questionId));
        Answer answer = this.answerRepository.findById(answerId).orElseThrow(() -> new AnswerNotFoundException(answerId));
        return getAnswerVoteResponseDTO(question, answer, answerVoteUpdate);
    }

    private AnswerVoteResponseDTO getAnswerVoteResponseDTO(Question question, Answer answer, AnswerVoteOperation answerVoteOperation) {
        AnswerVoteId answerVoteId = createVoteAnswerId(question, answer, answerVoteOperation.getUserId());
        AnswerVote answerVote = new AnswerVote();
        answerVote.setId(answerVoteId);
        answerVote.setScore(answerVoteOperation.getScore());
        answerVote.setCreatedAt(LocalDateTime.now());
        answerVote.setUpdatedAt(LocalDateTime.now());
        AnswerVote answerVoteSaved = this.answerVoteRepository.save(answerVote);
        this.calculadorScoreService.recalcularScore(answerVoteId.getUser(), answerVoteOperation.getScore());
        return AnswerVoteConverter.convert(answerVoteSaved);
    }

    private AnswerVoteId createVoteAnswerId(Question question, Answer answer, Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        AnswerVoteId answerVoteId = new AnswerVoteId();
        answerVoteId.setUser(user);
        answerVoteId.setQuestion(question);
        answerVoteId.setAnswer(answer);
        return answerVoteId;
    }
}
