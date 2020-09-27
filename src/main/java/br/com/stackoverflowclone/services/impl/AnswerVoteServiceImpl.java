package br.com.stackoverflowclone.services.impl;

import br.com.stackoverflowclone.converter.AnswerVoteConverter;
import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.AnswerVote;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.model.ids.AnswerVoteId;
import br.com.stackoverflowclone.repositories.AnswerVoteRepository;
import br.com.stackoverflowclone.request.AnswerVoteCreate;
import br.com.stackoverflowclone.request.AnswerVoteUpdate;
import br.com.stackoverflowclone.response.AnswerVoteResponseDTO;
import br.com.stackoverflowclone.services.AnswerVoteService;
import br.com.stackoverflowclone.services.UserReputationService;
import br.com.stackoverflowclone.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerVoteServiceImpl implements AnswerVoteService {

    private final AnswerVoteRepository answerVoteRepository;
    private final UserService userService;
    private final UserReputationService userReputationService;

    public AnswerVoteServiceImpl(AnswerVoteRepository answerVoteRepository,
                             UserService userService,
                             UserReputationService userReputationService) {
        this.answerVoteRepository = answerVoteRepository;
        this.userService = userService;
        this.userReputationService = userReputationService;
    }

    @Override
    public List<AnswerVote> findBy(Long questionId, Long userId) {
        return this.answerVoteRepository.findAllByUserIdAndQuestionId(userId, questionId);
    }

    @Override
    public AnswerVoteResponseDTO createVoteAnswer(Question question, Answer answer, AnswerVoteCreate answerVoteCreate) {
        AnswerVoteId answerVoteId = createVoteAnswerId(question, answer, answerVoteCreate.getUserId());
        AnswerVote answerVote = new AnswerVote();
        answerVote.setId(answerVoteId);
        answerVote.setScore(answerVoteCreate.getScore());
        answerVote.setCreatedAt(LocalDateTime.now());
        answerVote.setUpdatedAt(LocalDateTime.now());
        AnswerVote answerVoteSaved = this.answerVoteRepository.save(answerVote);
        this.userReputationService.recalcularScore(answerVoteId.getUser(), answerVoteCreate.getScore());
        return AnswerVoteConverter.convert(answerVoteSaved);
    }

    @Override
    public AnswerVoteResponseDTO updateVoteAnswer(Question question, Answer answer, AnswerVoteUpdate answerVoteUpdate) {
        AnswerVoteId answerVoteId = createVoteAnswerId(question, answer, answerVoteUpdate.getUserId());
        AnswerVote answerVote = new AnswerVote();
        answerVote.setId(answerVoteId);
        answerVote.setScore(answerVoteUpdate.getScore());
        answerVote.setCreatedAt(LocalDateTime.now());
        answerVote.setUpdatedAt(LocalDateTime.now());
        AnswerVote answerVoteSaved = this.answerVoteRepository.save(answerVote);
        this.userReputationService.recalcularScore(answerVoteId.getUser(), answerVoteUpdate.getScore());
        return AnswerVoteConverter.convert(answerVoteSaved);
    }

    private AnswerVoteId createVoteAnswerId(Question question, Answer answer, Long userId) {
        User user = this.userService.findById(userId);
        AnswerVoteId answerVoteId = new AnswerVoteId();
        answerVoteId.setUser(user);
        answerVoteId.setQuestion(question);
        answerVoteId.setAnswer(answer);
        return answerVoteId;
    }
}
