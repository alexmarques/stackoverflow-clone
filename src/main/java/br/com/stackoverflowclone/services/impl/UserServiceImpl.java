package br.com.stackoverflowclone.services.impl;

import br.com.stackoverflowclone.converter.UserConverter;
import br.com.stackoverflowclone.exceptions.UserNotFoundException;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.UserRepository;
import br.com.stackoverflowclone.request.UserCreate;
import br.com.stackoverflowclone.request.UserUpdate;
import br.com.stackoverflowclone.response.UserResponseDTO;
import br.com.stackoverflowclone.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserCreate userCreate) {
        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setEnabled(true);
        user.setBirthday(userCreate.getBirthday());
        user.setEmail(userCreate.getEmail());
        user.setName(userCreate.getName());
        User userSaved = this.userRepository.save(user);
        return UserConverter.convert(userSaved);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public UserResponseDTO updateUser(Long id, UserUpdate userUpdate) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setUpdatedAt(LocalDateTime.now());
        user.setBirthday(userUpdate.getBirthday());
        user.setName(userUpdate.getName());
        user.setEnabled(userUpdate.getEnabled());
        User userSaved = this.userRepository.save(user);
        return UserConverter.convert(userSaved);
    }

    public List<UserResponseDTO> findAll(String email, String name, LocalDate birthday, Pageable pageable) {
        return this.userRepository.findAll((Specification<User>) (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if(StringUtils.isNotEmpty(email)) {
                predicate = builder.and(predicate, builder.equal(root.get("email"), email));
            }
            if(StringUtils.isNotEmpty(name)) {
                predicate = builder.and(predicate, builder.like(root.get("name"), "%" + name + "%"));
            }
            if(Objects.nonNull(birthday)) {
                predicate = builder.and(predicate, builder.equal(root.get("birthday"), birthday));
            }
            return predicate;
        }, pageable).getContent()
                .stream()
                .map(UserConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
