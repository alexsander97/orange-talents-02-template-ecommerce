package com.example.mercadolivre.otherSystems;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OtherSystemsController {

    @PostMapping("/notas-fiscais")
    public void criaNotaFiscal(@RequestBody @Valid NewPurchaseNFRequest request) throws InterruptedException {
        System.out.println("criando nota para" + request);
        Thread.sleep(150);
    }

    @PostMapping("/ranking")
    public void ranking(@RequestBody @Valid NewPurchaseRankingRequest request) throws InterruptedException {
        System.out.println("criando ranking" + request);
        Thread.sleep(150);
    }
}
