package br.com.stackoverflowclone.controllers;

import br.com.stackoverflowclone.controllers.UserController;
import br.com.stackoverflowclone.services.UserReputationService;
import br.com.stackoverflowclone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class AlterarUsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserReputationService userReputationService;



}
