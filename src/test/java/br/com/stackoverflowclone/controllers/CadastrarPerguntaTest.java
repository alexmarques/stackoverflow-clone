package br.com.stackoverflowclone.controllers;

import br.com.stackoverflowclone.request.QuestionCreate;
import br.com.stackoverflowclone.services.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
public class CadastrarPerguntaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void deveCadastrarPerguntaSeForValida() throws Exception {
        QuestionCreate questionCreate = QuestionCreate.builder()
                .comment("comment")
                .flags(Collections.singletonList("flag1"))
                .userId(1L)
                .build();

        mockMvc.perform(post("/questions")
                .content(objectMapper.writeValueAsString(questionCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void deveFalharQuandoComentarioNaoPreenchido() throws Exception {
        QuestionCreate questionCreate = QuestionCreate.builder()
                .flags(Collections.singletonList("flag1"))
                .userId(1L)
                .build();

        mockMvc.perform(post("/questions")
                .content(objectMapper.writeValueAsString(questionCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveFalharQuandoUsuarioNaoPreenchido() throws Exception {
        QuestionCreate questionCreate = QuestionCreate.builder()
                .comment("comment")
                .flags(Collections.singletonList("flag1"))
                .build();

        mockMvc.perform(post("/questions")
                .content(objectMapper.writeValueAsString(questionCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveFalharQuandoFlagsNaoPreenchido() throws Exception {
        QuestionCreate questionCreate = QuestionCreate.builder()
                .comment("comment")
                .userId(1L)
                .build();

        mockMvc.perform(post("/questions")
                .content(objectMapper.writeValueAsString(questionCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}
