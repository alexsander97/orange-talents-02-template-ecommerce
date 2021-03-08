package com.example.mercadolivre.newPurchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NewPurchaseEvents {


    @Autowired
    private Set<PurchaseSuccessEvent> purchaseSuccessEvents;


    public void processing(Purchase purchase) {
        if (purchase.successfullyProcessed()) {
            purchaseSuccessEvents.forEach(event -> event.processing(purchase));
        } else {

        }
    }
}
