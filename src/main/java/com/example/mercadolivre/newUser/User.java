package com.example.mercadolivre.newUser;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email @NotBlank
    @Column(name = "EMAIL", nullable = false, unique = true)
    private  String email;

    @NotBlank
    @Length(min = 6)
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    private LocalDateTime creationDate = LocalDateTime.now();

    @Deprecated
    public User() {

    }


    public User(@Email @NotBlank String email, @NotBlank @Length(min = 6) String password) {
//        Asserts...
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}

