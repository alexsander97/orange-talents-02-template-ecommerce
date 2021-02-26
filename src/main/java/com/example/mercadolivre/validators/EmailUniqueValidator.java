package com.example.mercadolivre.validators;

import com.example.mercadolivre.newUser.NewUserRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class EmailUniqueValidator implements Validator {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean supports(Class<?> aClass) {
        return NewUserRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        NewUserRequest request = (NewUserRequest) target;
        Query query = entityManager.createQuery("select u from User u where u.email = :email");
        query.setParameter("email", request.getEmail());

        if(!query.getResultList().isEmpty()) {
            errors.rejectValue("email", null,
                    "Este email já está cadastrado: " + request.getEmail());
        }
    }
}
