package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.dto.UserDTO;
import br.com.stackoverflowclone.exceptions.UserNotFoundException;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setEnabled(true);
        user.setBirthday(userDTO.getBirthday());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        return this.userRepository.save(user);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        user.setUpdatedAt(LocalDateTime.now());
        user.setBirthday(userDTO.getBirthday());
        user.setName(userDTO.getName());
        return this.userRepository.save(user);
    }
}
