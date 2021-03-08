package com.example.mercadolivre.controllers;

import com.example.mercadolivre.newCategory.Category;
import com.example.mercadolivre.newProduct.NewCharacteristicRequest;
import com.example.mercadolivre.newProduct.NewProductRequest;
import com.example.mercadolivre.newUser.CleanPassword;
import com.example.mercadolivre.newUser.User;
import com.example.mercadolivre.validators.AvoidCharacteristicWithSameNameValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
@Transactional
public class ProductControllerTest {

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
    @DisplayName("não deve realizar cadastro de um product sem caracteristicas")
    @WithUserDetails("testando@email.com")
    public void shouldNotCreateProductIfNotExistsCharacteristics() throws Exception {
        List<NewCharacteristicRequest> characteristics = new ArrayList<>();
        Category category = new Category("Categoria de um produto", null);
        entityManager.persist(category);


        NewProductRequest request = new NewProductRequest("Um produto", new BigDecimal(100), 10, characteristics,
                "Descrição de um produto", 1L);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("deve realizar cadastro de um product com 3 caracteristicas")
    @WithUserDetails("testando@email.com")
    public void shouldCreateProductIfExistsCharacteristics() throws Exception {
        List<NewCharacteristicRequest> characteristics = Arrays.asList(new NewCharacteristicRequest("Um nome2", "Uma descrição"),
                new NewCharacteristicRequest("Outro nome2", "Outra descrição"),
                new NewCharacteristicRequest("Mais um nome2", "Mais uma descrição"));

        Category category = new Category("Categoria de um produto", null);
        entityManager.persist(category);

        NewProductRequest request = new NewProductRequest("Um produto 2", new BigDecimal(100), 10, characteristics,
                "Descrição de um produto", category.getId());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }



    @Test
    @DisplayName("Não deve retornar erro no campo caracteristica")
    @WithMockUser(username = "test@email.com", password = "123456")
    public void test3() {
        AvoidCharacteristicWithSameNameValidator validator = new AvoidCharacteristicWithSameNameValidator(entityManager);

        List<NewCharacteristicRequest> characteristics = Arrays.asList(new NewCharacteristicRequest("Um nome", "Uma descrição"),
                new NewCharacteristicRequest("Outro nome", "Outra descrição"),
                new NewCharacteristicRequest("Mais um nome", "Mais uma descrição"));
        //cria uma categoria para o teste
        Category category = entityManager.merge(new Category("Testandooo", null));


        Object target = new NewProductRequest("nome", new BigDecimal(10), 10, characteristics, "test", category.getId());

        Errors errors = new BeanPropertyBindingResult(target, "teste");

        validator.validate(target, errors);

        Assertions.assertEquals(false, errors.hasFieldErrors("characteristics"));
    }

    @Test
    @DisplayName("Deve retornar erro no campo caracteristica aonde existem campos iguais")
    @WithMockUser(username = "test@email.com", password = "123456")
    public void test4() {
        AvoidCharacteristicWithSameNameValidator validator = new AvoidCharacteristicWithSameNameValidator(entityManager);

        List<NewCharacteristicRequest> characteristics = Arrays.asList(new NewCharacteristicRequest("Mesmo nome", "Uma descrição"),
                new NewCharacteristicRequest("Mesmo nome", "Outra descrição"),
                new NewCharacteristicRequest("Mais um nome", "Mais uma descrição"));
        //cria uma categoria para o teste
        Category category = entityManager.merge(new Category("Testandooo", null));


        Object target = new NewProductRequest("nome", new BigDecimal(10), 10, characteristics, "test", category.getId());

        Errors errors = new BeanPropertyBindingResult(target, "teste");

        validator.validate(target, errors);

        Assertions.assertEquals(true, errors.hasFieldErrors("characteristics"));
    }

}