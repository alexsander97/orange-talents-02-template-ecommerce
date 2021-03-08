package com.example.mercadolivre.otherSystems;

import javax.validation.constraints.NotNull;

public class NewPurchaseRankingRequest {

    @NotNull
    private Long idPurchase;

    @NotNull
    private Long idSeller;

    public NewPurchaseRankingRequest(@NotNull Long idPurchase, @NotNull Long idSeller) {
        this.idPurchase = idPurchase;
        this.idSeller = idSeller;
    }

    @Override
    public String toString() {
        return "NewPurchaseRankingRequest{" +
                "idPurchase=" + idPurchase +
                ", idSeller=" + idSeller +
                '}';
    }

    public Long getIdPurchase() {
        return idPurchase;
    }

    public Long getIdSeller() {
        return idSeller;
    }
}
