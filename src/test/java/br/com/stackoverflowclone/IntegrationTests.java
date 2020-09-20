package br.com.stackoverflowclone;

import br.com.stackoverflowclone.dto.QuestionResponseDTO;
import br.com.stackoverflowclone.model.Answer;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.operations.question.QuestionCreate;
import br.com.stackoverflowclone.repositories.operations.user.UserCreate;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    @Order(1)
    void testCreateUser() {
        UserCreate userCreate = new UserCreate();
        userCreate.setBirthday(LocalDate.of(1985, 8, 10));
        userCreate.setEmail("alex@email.com");
        userCreate.setName("Alex");
        ResponseEntity<User> response = this.testRestTemplate.postForEntity("http://localhost:" + port + "/users", userCreate, User.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    void testCreateSecondUser() {
        UserCreate userCreate = new UserCreate();
        userCreate.setBirthday(LocalDate.of(1985, 8, 10));
        userCreate.setEmail("john@email.com");
        userCreate.setName("John");
        ResponseEntity<User> response = this.testRestTemplate.postForEntity("http://localhost:" + port + "/users", userCreate, User.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(3)
    void testGetAllUsers() {
        ResponseEntity<User[]> responseGetUsers = this.testRestTemplate.getForEntity("http://localhost:" + port + "/users", User[].class);
        assertThat(responseGetUsers.getBody().length).isEqualTo(2);
    }

    @Test
    @Order(4)
    void testCreateQuestion() {
        QuestionCreate questionCreate = new QuestionCreate();
        questionCreate.setUserId(1L);
        questionCreate.setComment("questao 1");
        questionCreate.setFlags(Arrays.asList("spring"));
        ResponseEntity<QuestionResponseDTO> response = this.testRestTemplate.postForEntity("http://localhost:" + port + "/questions", questionCreate, QuestionResponseDTO.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(5)
    void testCreateAnswer() {
    }

}
