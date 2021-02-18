package br.com.stackoverflowclone.controllers;

import br.com.stackoverflowclone.request.UserCreate;
import br.com.stackoverflowclone.controllers.UserController;
import br.com.stackoverflowclone.response.UserResponseDTO;
import br.com.stackoverflowclone.services.UserReputationService;
import br.com.stackoverflowclone.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class CadastroUsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserReputationService userReputationService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void deveFalharQuandoNomeNaoEstiverPreenchido() throws Exception {

        UserCreate userCreate = UserCreate.builder()
                .email("alex@email.com")
                .birthday(LocalDate.now().minusYears(18))
                .build();

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deveFalharQuandoEmailNaoEstiverPreenchido() throws Exception {

        UserCreate userCreate = UserCreate.builder()
                .name("Alex")
                .birthday(LocalDate.now().minusYears(18))
                .build();

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(userCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors[0].fieldName").value("email"))
                .andExpect(jsonPath("$.errors[0].message").value("Preenchimento obrigatorio"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveFalharQuandoEmailForInvalido() throws Exception {

        UserCreate userCreate = UserCreate.builder()
                .email("invalido")
                .name("Alex")
                .birthday(LocalDate.now().minusYears(18))
                .build();

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(userCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Email invalido"));
    }

    @Test
    public void deveFalharQuandoNascimentoNaoPreenchido() throws Exception {
        UserCreate userCreate = UserCreate.builder()
                .name("Alex")
                .email("alex@email.com")
                .build();

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(userCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveTerSucessoAoCadastrarUsuarioValido() throws Exception {

        UserCreate userCreate = UserCreate.builder()
                .name("Alex")
                .email("alex@email.com")
                .birthday(LocalDate.now().minusYears(18))
                .build();

        when(userService.createUser(userCreate)).thenReturn(UserResponseDTO.builder().build());

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(userCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
