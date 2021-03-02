package com.example.mercadolivre.newQuestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class Emails {

    @Autowired
    private Mailer mailer;

    public void newQuestion(@NotNull @Valid Question question) {
        mailer.send("<html>...</html>", "Nova pegunta..",
                question.getUser().getEmail(), "novapergunta@nossomercadolivre.com",
                question.getProductOwner().getEmail());
    }
}
