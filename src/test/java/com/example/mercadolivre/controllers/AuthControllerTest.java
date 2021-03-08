package com.example.mercadolivre.controllers;

import com.example.mercadolivre.newUser.CleanPassword;
import com.example.mercadolivre.newUser.NewUserRequest;
import com.example.mercadolivre.newUser.User;
import com.example.mercadolivre.security.LoginInputDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
@Transactional
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void before() {
        User user = new User("test@email.com", new CleanPassword("123456"));
        entityManager.persist(user);
    }

    @Test
    @DisplayName("deve autenticar o usuário válido")
    public void teste() throws Exception {
        LoginInputDto request = new LoginInputDto("test@email.com", "123456");

        ResultActions resultActions = mockMvc.perform(
                post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("não deve autenticar o usuário inválido")
    public void teste2() throws Exception {
        LoginInputDto request = new LoginInputDto("invalido@email.com", "123456");

        ResultActions resultActions = mockMvc.perform(
                post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
