package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.converter.UserReputationConverter;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.model.UserReputation;
import br.com.stackoverflowclone.repositories.AnswerVoteRepository;
import br.com.stackoverflowclone.repositories.UserReputationRepository;
import br.com.stackoverflowclone.request.UserReputationCreate;
import br.com.stackoverflowclone.request.UserReputationUpdate;
import br.com.stackoverflowclone.response.UserReputationResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserReputationService {

    private final UserReputationRepository userReputationRepository;
    private final AnswerVoteRepository answerVoteRepository;
    private final UserService userService;

    public UserReputationService(UserReputationRepository userReputationRepository,
                                 AnswerVoteRepository answerVoteRepository,
                                 UserService userService) {
        this.userReputationRepository = userReputationRepository;
        this.answerVoteRepository = answerVoteRepository;
        this.userService = userService;
    }

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

    public UserReputationResponseDTO create(Long userId, UserReputationCreate userReputationCreate) {
        User user = this.userService.findById(userId);
        UserReputation userReputation = new UserReputation();
        userReputation.setScore(userReputationCreate.getScore());
        userReputation.setUser(user);
        userReputation.setCreatedAt(LocalDateTime.now());
        userReputation.setUpdatedAt(LocalDateTime.now());
        UserReputation userReputationSaved = this.userReputationRepository.save(userReputation);
        return UserReputationConverter.convert(userReputationSaved);
    }

    public UserReputationResponseDTO findByUserId(Long userId) {
        UserReputation userReputation = this.userReputationRepository.findByUserId(userId);
        return UserReputationConverter.convert(userReputation);
    }

    public UserReputationResponseDTO update(Long userId, UserReputationUpdate userReputationUpdate) {
        UserReputation userReputation = this.userReputationRepository.findByUserId(userId);
        userReputation.setScore(userReputation.getScore() + userReputationUpdate.getScore());
        userReputation.setUpdatedAt(LocalDateTime.now());
        UserReputation userReputationSaved = this.userReputationRepository.save(userReputation);
        return UserReputationConverter.convert(userReputationSaved);
    }
}