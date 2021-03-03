package com.example.mercadolivre.newPurchase;

public enum StatusPayment {

    INICIADO("Compra iniciada.");

    private String description;

    StatusPayment(String description) {
        this.description = description;
    }
}
