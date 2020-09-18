package br.com.stackoverflowclone.resources;

import br.com.stackoverflowclone.dto.UserDTO;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@Api("Users")
public class UserResource {

    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return this.userService.findAll();
    }

    @PostMapping
    public User create(@Validated @RequestBody UserDTO user) {
        return this.userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Validated @RequestBody UserDTO user) {
        return this.userService.updateUser(id, user);
    }

}
