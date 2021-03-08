package com.example.mercadolivre.controllers;

import com.example.mercadolivre.newCategory.Category;
import com.example.mercadolivre.newCategory.NewCategoryRequest;
import com.example.mercadolivre.newProduct.NewCharacteristicRequest;
import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newPurchase.GatewayPayment;
import com.example.mercadolivre.newPurchase.NewPurchaseRequest;
import com.example.mercadolivre.newUser.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
@Transactional
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("deve realizar o cadastro de uma compra usando o paypal")
    @WithUserDetails("testando@email.com")
    public void teste1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new NewPurchaseRequest(createProduct().getId(), 1, GatewayPayment.PAYPAL))));
    }

    @Test
    @DisplayName("deve realizar o cadastro de uma compra usando o pagseguro")
    @WithUserDetails("testando@email.com")
    public void teste2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new NewPurchaseRequest(createProduct().getId(), 1, GatewayPayment.PAGSEGURO))));
    }

    private Product createProduct() {
        List<NewCharacteristicRequest> characteristics = Arrays.asList(new NewCharacteristicRequest("Um nome", "Uma descrição"),
                new NewCharacteristicRequest("Outro nome", "Outra descrição"),
                new NewCharacteristicRequest("Mais um nome", "Mais uma descrição"));
        Category category = new Category("categoria", null);
        entityManager.persist(category);

//        busca o usuario para criar o produto.
        User user = (User) entityManager
                .createQuery("select u from User u where u.email = :email")
                .setParameter("email", "testando@email.com").getResultList().get(0);


        Product product = new Product("nome", new BigDecimal(10), 10, characteristics, "test", category, user);

        entityManager.persist(product);
        return product;
    }

}
