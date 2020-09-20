package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.exceptions.UserNotFoundException;
import br.com.stackoverflowclone.model.AnswerVote;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.UserRepository;
import br.com.stackoverflowclone.repositories.operations.user.UserCreate;
import br.com.stackoverflowclone.repositories.operations.user.UserUpdate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private UserRepository userRepository;
    private AnswerVoteService answerVoteService;
    private UserReputationService userReputationService;

    public UserService(UserRepository userRepository,
                       AnswerVoteService answerVoteService,
                       UserReputationService userReputationService) {
        this.userRepository = userRepository;
        this.answerVoteService = answerVoteService;
        this.userReputationService = userReputationService;
    }

    public User createUser(UserCreate userCreate) {
        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setEnabled(true);
        user.setBirthday(userCreate.getBirthday());
        user.setEmail(userCreate.getEmail());
        user.setName(userCreate.getName());
        return this.userRepository.save(user);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User updateUser(Long id, UserUpdate userUpdate) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setUpdatedAt(LocalDateTime.now());
        user.setBirthday(userUpdate.getBirthday());
        user.setName(userUpdate.getName());
        user.setEnabled(userUpdate.getEnabled());
        return this.userRepository.save(user);
    }

    public List<User> findAll(String email, String name, LocalDate birthday, Pageable pageable) {
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
        }, pageable).getContent();
    }

    public User findById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
