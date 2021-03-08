package com.example.mercadolivre.newPurchase;

import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotaFiscal implements PurchaseSuccessEvent{

    @Override
    public void processing(Purchase purchase) {
        Assert.isTrue(purchase.successfullyProcessed(), "Compra não processada com sucesso." + purchase);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idPurchase", purchase.getId(),
                "idBuyer", purchase.getUser().getId());

        restTemplate.postForEntity("http://localhost:8080/notas-fiscais",
                request, String.class);
    }
}
