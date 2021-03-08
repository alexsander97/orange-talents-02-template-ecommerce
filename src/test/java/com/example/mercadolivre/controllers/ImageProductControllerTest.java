package com.example.mercadolivre.controllers;

import com.example.mercadolivre.newCategory.Category;
import com.example.mercadolivre.newProduct.NewCharacteristicRequest;
import com.example.mercadolivre.newProduct.NewProductRequest;
import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newUser.CleanPassword;
import com.example.mercadolivre.newUser.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestEntityManager
public class ImageProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Test
    @WithUserDetails("testando@email.com")
    @DisplayName("Deve retornar 200 se tiver tudo ok com a imagem")
    public void teste() throws Exception {

        MockMultipartFile file = new MockMultipartFile("images", "image1.png", null, (InputStream) null);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/products/" + createProduct().getId() + "/images")
        .file(file)).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("Deve retornar true com usuario igual ao que criou o produto")
    void teste2() {

        Product product = createProduct();
        boolean result = product.belongsToUser(product.getUser());

        assertEquals(true, result);
    }

    @Test
    @DisplayName("Deve retornar falso com usuario diferente do que criou o produto")
    void teste3() {

        Product product = createProduct();
        boolean result = product.belongsToUser(new User("email@email.com", new CleanPassword("123456")));

        assertEquals(false, result);
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
