package com.example.mercadolivre.validators;

import com.example.mercadolivre.newProduct.NewProductRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class AvoidCharacteristicWithSameNameValidator implements Validator {
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
