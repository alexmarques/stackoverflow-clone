package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.UserReputation;
import br.com.stackoverflowclone.repositories.UserReputationRepository;
import org.springframework.stereotype.Service;

@Service
public class UserReputationService {

    private final UserReputationRepository userReputationRepository;
    private final AnswerVoteService answerVoteService;

    public UserReputationService(UserReputationRepository userReputationRepository,
                                 AnswerVoteService answerVoteService) {
        this.userReputationRepository = userReputationRepository;
        this.answerVoteService = answerVoteService;
    }

    public void recalcularScore(Question question) {
        question.getAnswers().stream().forEach(answer -> {
            UserReputation userReputation = this.userReputationRepository.findByUserId(answer.getUser().getId());
            this.answerVoteService.findBy(question.getId(), answer.getUser().getId()).stream().forEach(answerVote -> {
                userReputation.setScore(userReputation.getScore() - answerVote.getScore());
            });
            this.userReputationRepository.save(userReputation);
        });
    }
}
