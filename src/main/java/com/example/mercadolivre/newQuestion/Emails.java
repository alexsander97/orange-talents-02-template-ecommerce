package com.example.mercadolivre.newQuestion;

import com.example.mercadolivre.newPurchase.Purchase;
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

    public void newPurchase(@NotNull @Valid Purchase purchase) {
        mailer.send("<html>...</html>", "Usuário tal disse que queria comprar o seu produto..",
                purchase.getUser().getEmail(), "novapergunta@nossomercadolivre.com",
                purchase.getProduct().getUser().getEmail());
    }
}
