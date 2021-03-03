package com.example.mercadolivre.newPurchase;

public enum GatewayPayment {

    PAYPAL("paypal.com/{idGeradoDaCompra}"),
    PAGSEGURO("pagseguro.com?returnId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}");

    private String description;

    GatewayPayment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
