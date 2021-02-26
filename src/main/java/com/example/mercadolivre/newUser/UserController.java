package com.example.mercadolivre.newUser;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid NewUserRequest request) {
        User user = request.toEntity();
        entityManager.persist(user);
    }
}
