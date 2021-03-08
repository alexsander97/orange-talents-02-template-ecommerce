package com.example.mercadolivre.controllers;

import com.example.mercadolivre.newUser.CleanPassword;
import com.example.mercadolivre.newUser.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
@Transactional
public class UserControllerTest {

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
    @DisplayName("deve realizar o cadastro de um usuário")
    public void shouldCreateNewUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new User("testando2@email.com",
                        new CleanPassword("123456")))))
                .andExpect(status().isOk());
    }

    @Test()
    @DisplayName("não deve realizar o cadastro de um usuário com email já existe")
    @WithMockUser(username = "test@email.com", password = "123456")
    public void notShouldCreateNewUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new User("test@email.com",
                        new CleanPassword("123456")))))
                .andExpect(status().isBadRequest());
    }
}
