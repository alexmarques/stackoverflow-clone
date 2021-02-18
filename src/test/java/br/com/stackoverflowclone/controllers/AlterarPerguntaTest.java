package br.com.stackoverflowclone.controllers;

import br.com.stackoverflowclone.model.Flag;
import br.com.stackoverflowclone.model.Question;
import br.com.stackoverflowclone.model.User;
import br.com.stackoverflowclone.repositories.QuestionRepository;
import br.com.stackoverflowclone.request.QuestionCreate;
import br.com.stackoverflowclone.request.QuestionUpdate;
import br.com.stackoverflowclone.services.FlagService;
import br.com.stackoverflowclone.services.QuestionService;
import br.com.stackoverflowclone.services.impl.QuestionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
public class AlterarPerguntaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionServiceImpl questionService;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private FlagService flagService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeTests() {
        ReflectionTestUtils.setField(questionService, "questionRepository", questionRepository);
    }

    @Test
    public void deveAtualizarDataAtualizacaoQuandoAtualizarPergunta() throws Exception {

        QuestionCreate questionCreate = QuestionCreate.builder()
                .comment("comment")
                .userId(1L)
                .flags(Collections.singletonList("flag1"))
                .build();

        Question question = Question.builder()
                .user(User.builder().id(1L).build())
                .build();

        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(new Question()));
        when(flagService.insert(anyString())).thenReturn(new Flag());
        when(questionRepository.save(any(Question.class))).thenReturn(question);
        when(questionService.update(anyLong(), any(QuestionUpdate.class))).thenCallRealMethod();

        mockMvc.perform(put("/questions/{id}", 1L)
                .content(objectMapper.writeValueAsString(questionCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
