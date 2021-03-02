package com.example.mercadolivre.newQuestion;

import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newUser.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Question implements Comparable<Question>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    private LocalDateTime dateCreation;

    @Deprecated
    public Question() {

    }

    public Question(@NotBlank String title, Product product, User user) {
        this.title = title;
        this.product = product;
        this.user = user;
        this.dateCreation = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public User getProductOwner() {
        return product.getUser();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(Question o) {
        return this.title.compareTo(o.title);
    }
}
