package com.example.mercadolivre.controllers;

import com.example.mercadolivre.newCategory.Category;
import com.example.mercadolivre.newCategory.NewCategoryRequest;
import com.example.mercadolivre.newUser.CleanPassword;
import com.example.mercadolivre.newUser.User;
import com.example.mercadolivre.validators.CategoryExistsValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
@Transactional
public class CategoryControllerTest {

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
    @DisplayName("deve realizar o cadastro de uma categoria")
    @WithMockUser(username = "test@email.com", password = "123456")
    public void shouldCreateNewCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new NewCategoryRequest("testt",
                        null))));
    }

    @Test()
    @DisplayName("não deve realizar o cadastro de uma categoria com nome já existe")
    @WithMockUser(username = "test@email.com", password = "123456")
    public void notShouldCreateNewCategoryWithNameExists() throws Exception {
        Category category = new Category("Testando", null);
        entityManager.persist(category);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new NewCategoryRequest("Testando",
                        null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("não deve realizar o cadastro de uma categoria com categoria mãe não existente")
    @WithMockUser(username = "test@email.com", password = "123456")
    public void shouldNotCreateCategoryIfNotExistsCategoryMother() {
        CategoryExistsValidator validator = new CategoryExistsValidator(entityManager);

        Object target = new NewCategoryRequest("testandoIdCategoryMotherNaoExiste",
                13728923489L);
        Errors errors = new BeanPropertyBindingResult(target, "teste");

        validator.validate(target, errors);

        Assertions.assertEquals(true, errors.hasFieldErrors("idCategoryMother"));
    }

    @Test
    @DisplayName("deve realizar o cadastro de uma categoria com categoria mãe existente")
    @WithMockUser(username = "test@email.com", password = "123456")
    public void shouldCreateCategoryIfExistsCategoryMother() {
        CategoryExistsValidator validator = new CategoryExistsValidator(entityManager);

//        cria uma categoria para que exista uma categoria mãe para ser testada
        Category categoryMother = entityManager.merge(new Category("Testandooo", null));

        Object target = new NewCategoryRequest("testandoIdCategoryMotherExiste",
                categoryMother.getId());
        Errors errors = new BeanPropertyBindingResult(target, "teste");

        validator.validate(target, errors);

        Assertions.assertEquals(false, errors.hasFieldErrors("idCategoryMother"));
    }

}
