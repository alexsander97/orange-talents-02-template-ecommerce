package com.example.mercadolivre.newCategory;

import com.example.mercadolivre.annotations.UniqueValue;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


public class NewCategoryRequest {

    @NotBlank
    @UniqueValue(domainClass = Category.class, fieldName = "name")
    private String name;

    @Positive
    private Long idCategoryMother;

    public NewCategoryRequest(@NotBlank String name, @Positive Long idCategoryMother) {
        this.name = name;
        this.idCategoryMother = idCategoryMother;
    }

    public String getName() {
        return name;
    }

    public Long getIdCategoryMother() {
        return idCategoryMother;
    }

    public Category toEntity(EntityManager entityManager) {
        Category category = null;
        if (idCategoryMother != null) {
            category = entityManager.find(Category.class, idCategoryMother);
        }
        return new Category(name, category);
    }
}
