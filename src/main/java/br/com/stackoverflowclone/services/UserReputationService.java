package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.request.UserReputationCreate;
import br.com.stackoverflowclone.request.UserReputationUpdate;
import br.com.stackoverflowclone.response.UserReputationResponseDTO;

public interface UserReputationService {
    UserReputationResponseDTO create(Long userId, UserReputationCreate userReputationCreate);
    UserReputationResponseDTO findByUserId(Long userId);
    UserReputationResponseDTO update(Long userId, UserReputationUpdate userReputationUpdate);
}