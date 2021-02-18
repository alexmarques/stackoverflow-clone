package br.com.stackoverflowclone.controllers;

import br.com.stackoverflowclone.request.UserCreate;
import br.com.stackoverflowclone.request.UserUpdate;
import br.com.stackoverflowclone.response.UserReputationResponseDTO;
import br.com.stackoverflowclone.response.UserResponseDTO;
import br.com.stackoverflowclone.services.UserReputationService;
import br.com.stackoverflowclone.services.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class UserController {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private final UserService userService;
    private final UserReputationService userReputationService;

    public UserController(UserService userService, UserReputationService userReputationService) {
        this.userService = userService;
        this.userReputationService = userReputationService;
    }

    @GetMapping("/users")
    public List<UserResponseDTO> findAllUsers(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false)
                              @DateTimeFormat(pattern = DATE_PATTERN) LocalDate birthday,
                              Pageable pageable) {
        return this.userService.findAll(email, name, birthday, pageable);
    }

    @PostMapping("/users")
    public UserResponseDTO createUser(@Validated @RequestBody UserCreate user) {
        return this.userService.createUser(user);
    }

    @PutMapping("/users/{id}")
    public UserResponseDTO updateUSer(@PathVariable Long id, @Validated @RequestBody UserUpdate user) {
        return this.userService.updateUser(id, user);
    }

    @GetMapping("/users/{userId}/reputation")
    public UserReputationResponseDTO findUserReputation(@PathVariable Long userId) {
        return this.userReputationService.findByUserId(userId);
    }
}
