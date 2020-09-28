package br.com.stackoverflowclone;

import br.com.stackoverflowclone.request.*;
import br.com.stackoverflowclone.response.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = StackoverflowCloneApplication.class)
@ComponentScan("br.com.stackoverflowclone")
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    void deveAtualizarAhReputacaoDoUsuarioQuandoAhPerguntaForDeletada() {
        UserResponseDTO alex = testCreateUser(LocalDate.of(1985, 8, 10), "alex@email.com", "Alex");
        UserResponseDTO john = testCreateUser(LocalDate.of(1985, 8, 10), "john@email.com", "John");
        UserResponseDTO alan = testCreateUser(LocalDate.of(1985, 8, 10), "alan@email.com", "Alan");
        QuestionResponseDTO alexQuestion = testCreateQuestion(alex.getUserId(), "questao alex", Arrays.asList("spring"));
        QuestionResponseDTO alanQuestion = testCreateQuestion(alan.getUserId(), "questao alan", Arrays.asList("spring"));
        AnswerResponseDTO respostaJohn = testCreateAnswer("resposta 1", alexQuestion.getQuestionId(), john.getUserId());
        AnswerResponseDTO segundaRepostaJohn = testCreateAnswer("responta 2", alanQuestion.getQuestionId(), john.getUserId());
        testCreateVoteAnswer(alexQuestion.getQuestionId(), respostaJohn.getAnswerId(), john.getUserId(), 5);
        testCreateVoteAnswer(alanQuestion.getQuestionId(), segundaRepostaJohn.getAnswerId(), john.getUserId(), 5);
        deleteQuestion(alanQuestion.getQuestionId());
        testRecalculoUserReputation(john.getUserId(), 5);
    }

    @Test
    void deveDeletarTodasAsRepostasQuandoAhQuestaoForDeletada() {
        UserResponseDTO jose = testCreateUser(LocalDate.of(1985, 8, 10), "jose@email.com", "Jose");
        UserResponseDTO rob = testCreateUser(LocalDate.of(1985, 8, 10), "rob@email.com", "Rob");
        UserResponseDTO ted = testCreateUser(LocalDate.of(1985, 8, 10), "ted@email.com", "Ted");
        QuestionResponseDTO questaoJose = testCreateQuestion(jose.getUserId(), "questao jose", Arrays.asList("microservices"));
        testCreateAnswer("reposta rob", questaoJose.getQuestionId(), rob.getUserId());
        testCreateAnswer("reposta ted", questaoJose.getQuestionId(), ted.getUserId());
        deleteQuestion(questaoJose.getQuestionId());
        String url = String.format("http://localhost:%s/questions/%s/answers", port, questaoJose.getQuestionId());
        ResponseEntity<AnswerResponseDTO[]> response =
                this.testRestTemplate.getForEntity(url, AnswerResponseDTO[].class);
        assertThat(response.getBody().length).isEqualTo(0);
    }

    private UserResponseDTO testCreateUser(LocalDate birthday, String email, String name) {
        UserCreate userCreate = new UserCreate();
        userCreate.setBirthday(birthday);
        userCreate.setEmail(email);
        userCreate.setName(name);
        String url = String.format("http://localhost:%s/users", port);
        ResponseEntity<UserResponseDTO> response = this.testRestTemplate.postForEntity(url, userCreate, UserResponseDTO.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private QuestionResponseDTO testCreateQuestion(Long userId, String comment, List<String> tags) {
        QuestionCreate questionCreate = new QuestionCreate();
        questionCreate.setUserId(userId);
        questionCreate.setComment(comment);
        questionCreate.setFlags(tags);
        String url = String.format("http://localhost:%s/questions", port);
        ResponseEntity<QuestionResponseDTO> response =
                this.testRestTemplate.postForEntity(url, questionCreate, QuestionResponseDTO.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private AnswerResponseDTO testCreateAnswer(String comment, Long questionId, Long userId) {
        AnswerCreate answerCreate = new AnswerCreate();
        answerCreate.setComment(comment);
        answerCreate.setQuestionId(questionId);
        answerCreate.setUserId(userId);
        String url = String.format("http://localhost:%s/questions/%s/answers", port, questionId);
        ResponseEntity<AnswerResponseDTO> response =
                this.testRestTemplate.postForEntity(url, answerCreate, AnswerResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        return response.getBody();
    }

    private void deleteQuestion(Long questionId) {
        String url = String.format("http://localhost:%s/questions/%s", port, questionId);
        this.testRestTemplate.delete(url);
    }

    void testRecalculoUserReputation(Long userId, Integer expected) {
        String url = String.format("http://localhost:%s/users/%s/reputation", port, userId);
        ResponseEntity<UserReputationResponseDTO> response = this.testRestTemplate.getForEntity(url, UserReputationResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getScore()).isEqualTo(expected);
    }

    void createVoteAnswer(Long questionId, Long answerId, Long userId, Integer score) {
        String url = String.format("http://localhost:%s/questions/%s/answers/%s/vote", port, questionId, answerId);
        AnswerVoteCreate answerVoteCreate = new AnswerVoteCreate();
        answerVoteCreate.setUserId(userId);
        answerVoteCreate.setScore(score);
        ResponseEntity<AnswerVoteResponseDTO> response = this.testRestTemplate.postForEntity(url, answerVoteCreate, AnswerVoteResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    void updateVoteAnswer(Long questionId, Long answerId, Long userId, Integer score) {
        String url = String.format("http://localhost:%s/questions/%s/answers/%s/vote", port, questionId, answerId);
        AnswerVoteUpdate answerVoteUpdate = new AnswerVoteUpdate();
        answerVoteUpdate.setUserId(userId);
        answerVoteUpdate.setScore(score);
        this.testRestTemplate.put(url, answerVoteUpdate);
    }

    AnswerVoteResponseDTO testCreateVoteAnswer(Long questionId, Long answerId, Long userId, Integer score) {
//        String url = "http://localhost:" + port + "/questions/{questionId}/answers/{answerId}/vote";
        String url = String.format("http://localhost:%s/questions/%s/answers/%s/vote", port, questionId, answerId);
        AnswerVoteCreate answerVoteCreate = new AnswerVoteCreate();
        answerVoteCreate.setUserId(userId);
        answerVoteCreate.setScore(score);
        ResponseEntity<AnswerVoteResponseDTO> response =
                this.testRestTemplate.postForEntity(url, answerVoteCreate, AnswerVoteResponseDTO.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    void testUpdateVoteAnswer(Long questionId, Long answerId, Long userId, Integer score) {
        String url = String.format("http://localhost:%s/questions/%s/answers/%s/vote", port, questionId, answerId);
        AnswerVoteUpdate answerVoteUpdate = new AnswerVoteUpdate();
        answerVoteUpdate.setUserId(userId);
        answerVoteUpdate.setScore(score);
        this.testRestTemplate.put(url, answerVoteUpdate);
    }

}
