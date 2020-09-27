package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.request.UserCreate;
import br.com.stackoverflowclone.request.UserUpdate;
import br.com.stackoverflowclone.response.UserResponseDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserCreate userCreate);
    List<User> findAll();
    UserResponseDTO updateUser(Long id, UserUpdate userUpdate);
    List<UserResponseDTO> findAll(String email, String name, LocalDate birthday, Pageable pageable);
    User findById(Long userId);
}
