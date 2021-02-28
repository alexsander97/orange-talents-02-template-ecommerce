package com.example.mercadolivre.newOpinion;

import com.example.mercadolivre.security.LoggedUser;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/opnions")
public class OpnionController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid NewOpnionRequest request, @AuthenticationPrincipal LoggedUser user) {
        Opnion opnion = request.toEntity(entityManager, user);
        entityManager.persist(opnion);
    }
}
