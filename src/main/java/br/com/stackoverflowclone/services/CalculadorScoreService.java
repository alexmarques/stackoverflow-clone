package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;

public interface CalculadorScoreService {
    void recalcularScore(Question question);

    void recalcularScore(User user, Integer score);
}
