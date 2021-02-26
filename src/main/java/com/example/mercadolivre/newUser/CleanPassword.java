package com.example.mercadolivre.newUser;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;

//representa uma senha limpa no sistema.
public class CleanPassword {

    private String senha;

    public CleanPassword(@NotBlank @Length(min = 6) String senha) {
        Assert.hasLength(senha, "não pode ser em branco");
        Assert.isTrue(senha.length() >= 6, "senha tem que ter no mínimo 6 caracteres.");
        this.senha = senha;
    }

    public String hash() {
        return new BCryptPasswordEncoder().encode(senha);
    }
}
