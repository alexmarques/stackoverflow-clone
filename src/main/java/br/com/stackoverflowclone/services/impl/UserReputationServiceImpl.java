package br.com.stackoverflowclone.services.impl;

import br.com.stackoverflowclone.converter.UserReputationConverter;
import br.com.stackoverflowclone.exceptions.UserNotFoundException;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.model.UserReputation;
import br.com.stackoverflowclone.repositories.UserRepository;
import br.com.stackoverflowclone.repositories.UserReputationRepository;
import br.com.stackoverflowclone.request.UserReputationCreate;
import br.com.stackoverflowclone.request.UserReputationUpdate;
import br.com.stackoverflowclone.response.UserReputationResponseDTO;
import br.com.stackoverflowclone.services.UserReputationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserReputationServiceImpl implements UserReputationService {

    private final UserReputationRepository userReputationRepository;
    private final UserRepository userRepository;

    public UserReputationServiceImpl(UserReputationRepository userReputationRepository,
                                     UserRepository userRepository) {
        this.userReputationRepository = userReputationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserReputationResponseDTO create(Long userId, UserReputationCreate userReputationCreate) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UserReputation userReputation = new UserReputation();
        userReputation.setScore(userReputationCreate.getScore());
        userReputation.setUser(user);
        userReputation.setCreatedAt(LocalDateTime.now());
        userReputation.setUpdatedAt(LocalDateTime.now());
        UserReputation userReputationSaved = this.userReputationRepository.save(userReputation);
        return UserReputationConverter.convert(userReputationSaved);
    }

    @Override
    public UserReputationResponseDTO findByUserId(Long userId) {
        UserReputation userReputation = this.userReputationRepository.findByUserId(userId);
        return UserReputationConverter.convert(userReputation);
    }

    @Override
    public UserReputationResponseDTO update(Long userId, UserReputationUpdate userReputationUpdate) {
        UserReputation userReputation = this.userReputationRepository.findByUserId(userId);
        userReputation.setScore(userReputation.getScore() + userReputationUpdate.getScore());
        userReputation.setUpdatedAt(LocalDateTime.now());
        UserReputation userReputationSaved = this.userReputationRepository.save(userReputation);
        return UserReputationConverter.convert(userReputationSaved);
    }
}
