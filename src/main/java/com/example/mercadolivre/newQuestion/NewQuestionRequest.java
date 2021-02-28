package com.example.mercadolivre.newQuestion;

import com.example.mercadolivre.annotations.ExistsId;
import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newUser.User;


import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewQuestionRequest {

    @NotBlank
    private String title;

    @NotNull
    @ExistsId(domainClass = Product.class, fieldName = "id")
    private Long idProduct;

    public Question toEntity(EntityManager entityManager, User user) {
        Product product = entityManager.find(Product.class, idProduct);
        return new Question(title, product, user);
    }

    public String getTitle() {
        return title;
    }

    public Long getIdProduct() {
        return idProduct;
    }
}
