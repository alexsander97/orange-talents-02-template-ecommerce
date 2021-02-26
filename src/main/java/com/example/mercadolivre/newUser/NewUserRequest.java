package com.example.mercadolivre.newUser;

import com.example.mercadolivre.annotations.UniqueValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NewUserRequest {

    @NotBlank
    @Email
    @UniqueValue(domainClass = User.class, fieldName = "email")
    private String email;

    @NotBlank
    @Length(min = 6)
    private String senha;

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public User toEntity() {
        return new User(this.email, new CleanPassword(this.senha));
    }
}
