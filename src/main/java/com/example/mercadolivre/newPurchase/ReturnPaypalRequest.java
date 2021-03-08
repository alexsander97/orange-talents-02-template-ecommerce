package com.example.mercadolivre.newPurchase;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ReturnPaypalRequest implements ReturnGatewayPayment {

    @Min(0)
    @Max(1)
    private int status;

    @NotBlank
    private String idTransaction;

    public ReturnPaypalRequest(@Min(0) @Max(1) int status, @NotBlank String idTransaction) {
        this.status = status;
        this.idTransaction = idTransaction;
    }

    public Transaction toTransaction(Purchase purchase) {
        StatusTransaction status = this.status == 0 ? StatusTransaction.ERRO
                : StatusTransaction.SUCESSO;

        return new Transaction(status, idTransaction, purchase);
    }
}
