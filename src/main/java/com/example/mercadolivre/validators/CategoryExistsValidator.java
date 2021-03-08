package com.example.mercadolivre.validators;

import com.example.mercadolivre.newCategory.Category;
import com.example.mercadolivre.newCategory.NewCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class CategoryExistsValidator implements Validator {


    private EntityManager entityManager;

    @Autowired
    public CategoryExistsValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewCategoryRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        NewCategoryRequest request = (NewCategoryRequest) target;

        if ( request.getIdCategoryMother() != null) {
            Category category = entityManager.find(Category.class, request.getIdCategoryMother());
            if(category == null) {
                errors.rejectValue("idCategoryMother", null,
                        "Esta categoria não está cadastrada.");
            }
        }
    }
}
