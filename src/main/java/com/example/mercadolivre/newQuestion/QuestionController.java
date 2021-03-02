package com.example.mercadolivre.newQuestion;

import com.example.mercadolivre.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Emails emails;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid NewQuestionRequest request, @AuthenticationPrincipal LoggedUser user) {
        Question question = request.toEntity(entityManager, user.get());
        entityManager.persist(question);
        emails.newQuestion(question);
    }
}
