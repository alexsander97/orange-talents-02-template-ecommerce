package com.example.mercadolivre.newCategory;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_MOTHER")
    private Category categoryMother;

    @Deprecated
    public Category() {

    }

    public Category(String name, Category category) {
        this.name = name;
        this.categoryMother = category;
    }
}
