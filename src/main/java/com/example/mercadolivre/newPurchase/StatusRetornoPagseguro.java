package com.example.mercadolivre.newPurchase;

public enum StatusRetornoPagseguro {
    SUCESSO, ERRO;

    public StatusTransaction normalizes() {
        if (this.equals(SUCESSO)) {
            return StatusTransaction.SUCESSO;
        }
        return StatusTransaction.ERRO;
    }
}
