package com.example.mercadolivre.newUser;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email @NotBlank
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Length(min = 6)
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @NotNull @PastOrPresent
    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Deprecated
    public User() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User(@Email @NotBlank String email, @NotBlank @Length(min = 6) CleanPassword cleanPassword) {
        Assert.isTrue(StringUtils.hasLength(email), "email não pode ser em branco");
        Assert.notNull(cleanPassword, "o objeto do tipo senha não pode ser nulo");
        this.email = email;
        this.password = cleanPassword.hash();
        this.creationDate = LocalDateTime.now();
    }
}

