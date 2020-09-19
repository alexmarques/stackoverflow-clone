package br.com.stackoverflowclone.resources;

import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.operations.user.UserCreate;
import br.com.stackoverflowclone.repositories.operations.user.UserUpdate;
import br.com.stackoverflowclone.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(value = "/users")
@Api("Users")
public class UserResource {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false)
                              @DateTimeFormat(pattern = DATE_PATTERN) LocalDate birthday,
                              Pageable pageable) {
        return this.userService.findAll(email, name, birthday, pageable);
    }

    @PostMapping
    public User create(@Validated @RequestBody UserCreate user) {
        return this.userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Validated @RequestBody UserUpdate user) {
        return this.userService.updateUser(id, user);
    }

}
