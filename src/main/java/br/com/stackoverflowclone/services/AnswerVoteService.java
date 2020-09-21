package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.AnswerVote;
import br.com.stackoverflowclone.repositories.AnswerVoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerVoteService {

    private final AnswerVoteRepository answerVoteRepository;

    public AnswerVoteService(AnswerVoteRepository answerVoteRepository) {
        this.answerVoteRepository = answerVoteRepository;
    }

    public List<AnswerVote> findBy(Long questionId, Long userId) {
        return this.answerVoteRepository.findAllByUserIdAndQuestionId(userId, questionId);
    }
}
