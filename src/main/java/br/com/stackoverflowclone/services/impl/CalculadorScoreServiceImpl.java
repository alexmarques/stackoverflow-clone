package br.com.stackoverflowclone.services.impl;

import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.model.UserReputation;
import br.com.stackoverflowclone.repositories.AnswerVoteRepository;
import br.com.stackoverflowclone.repositories.UserReputationRepository;
import br.com.stackoverflowclone.services.CalculadorScoreService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CalculadorScoreServiceImpl implements CalculadorScoreService {

    private final UserReputationRepository userReputationRepository;
    private final AnswerVoteRepository answerVoteRepository;

    public CalculadorScoreServiceImpl(UserReputationRepository userReputationRepository,
                                      AnswerVoteRepository answerVoteRepository) {
        this.userReputationRepository = userReputationRepository;
        this.answerVoteRepository = answerVoteRepository;
    }

    @Override
    public void recalcularScore(Question question) {
        question.getAnswers().stream().forEach(answer -> {
            UserReputation userReputation = this.userReputationRepository.findByUserId(answer.getUser().getId());
            this.answerVoteRepository.findAllByUserIdAndQuestionId(answer.getUser().getId(), question.getId())
                    .stream()
                    .forEach(answerVote -> {
                        userReputation.setScore(userReputation.getScore() - answerVote.getScore());
                    });
            this.userReputationRepository.save(userReputation);
        });
    }

    @Override
    public void recalcularScore(User user, Integer score) {
        Optional<UserReputation> userReputationOptional = this.userReputationRepository.findById(user.getId());
        if(userReputationOptional.isPresent()) {
            UserReputation userReputation = userReputationOptional.get();
            userReputation.setScore(userReputation.getScore() + score);
            this.userReputationRepository.save(userReputation);
        } else {
            UserReputation userReputation = new UserReputation();
            userReputation.setScore(score);
            userReputation.setUpdatedAt(LocalDateTime.now());
            userReputation.setCreatedAt(LocalDateTime.now());
            userReputation.setUser(user);
            this.userReputationRepository.save(userReputation);
        }
    }

}
