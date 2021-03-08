package com.example.mercadolivre.newPurchase;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReturnPagSeguroRequest implements ReturnGatewayPayment {

    @NotBlank
    private String idTransaction;

    @NotNull
    private StatusRetornoPagseguro status;

    public ReturnPagSeguroRequest(@NotBlank String idTransaction, @NotNull StatusRetornoPagseguro status) {
        this.idTransaction = idTransaction;
        this.status = status;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public StatusRetornoPagseguro getStatus() {
        return status;
    }

    public Transaction toTransaction(Purchase purchase) {
        return new Transaction(status.normalizes(), idTransaction, purchase);
    }
}
