package com.example.mercadolivre.validators;

import com.example.mercadolivre.newProduct.NewProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Set;

@Component
public class AvoidCharacteristicWithSameNameValidator implements Validator {

    private EntityManager entityManager;

    @Autowired
    public AvoidCharacteristicWithSameNameValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NewProductRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) {
            return;
        }

        NewProductRequest request = (NewProductRequest) target;
        Set<String> nameEquals = request.lookingEqualCharacteristics();
        if (!nameEquals.isEmpty()) {
            errors.rejectValue("characteristics", null, "Existe caracteristicas iguais." + nameEquals);
        }
    }
}
