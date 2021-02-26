package com.example.mercadolivre.newUser;

import com.example.mercadolivre.validators.EmailUniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmailUniqueValidator emailUniqueValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(emailUniqueValidator);
    }

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid NewUserRequest request) {
        User user = request.toEntity();
        entityManager.persist(user);
    }
}
