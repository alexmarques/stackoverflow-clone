package br.com.stackoverflowclone.converter;

import br.com.stackoverflowclone.model.UserReputation;
import br.com.stackoverflowclone.response.UserReputationResponseDTO;

public class UserReputationConverter {

    public static UserReputationResponseDTO convert(UserReputation userReputation) {
        UserReputationResponseDTO userReputationResponseDTO = new UserReputationResponseDTO();
        userReputationResponseDTO.setCreatedAt(userReputation.getCreatedAt());
        userReputationResponseDTO.setUpdadetAt(userReputation.getCreatedAt());
        userReputationResponseDTO.setUserReputationId(userReputation.getId());
        userReputationResponseDTO.setUserId(userReputation.getUser().getId());
        userReputationResponseDTO.setScore(userReputation.getScore());
        return userReputationResponseDTO;
    }
}
