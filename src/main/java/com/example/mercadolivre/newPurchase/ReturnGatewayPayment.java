package com.example.mercadolivre.newPurchase;

public interface ReturnGatewayPayment {

    Transaction toTransaction(Purchase purchase);
}
