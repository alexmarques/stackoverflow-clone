package br.com.stackoverflowclone.converter;

import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.response.UserResponseDTO;

public class UserConverter {
    public static UserResponseDTO convert(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setBirthday(user.getBirthday());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setUpdatedAt(user.getUpdatedAt());
        userResponseDTO.setEnabled(user.getEnabled());
        return userResponseDTO;

    }
}
