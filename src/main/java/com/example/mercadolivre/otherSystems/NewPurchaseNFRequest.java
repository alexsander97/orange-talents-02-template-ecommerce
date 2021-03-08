package com.example.mercadolivre.otherSystems;

import javax.validation.constraints.NotNull;

public class NewPurchaseNFRequest {

    @NotNull
    private Long idPurchase;

    @NotNull
    private Long idBuyer;

    public NewPurchaseNFRequest(Long idPurchase, Long idBuyer) {
        this.idPurchase = idPurchase;
        this.idBuyer = idBuyer;
    }

    public Long getIdPurchase() {
        return idPurchase;
    }

    public Long getIdBuyer() {
        return idBuyer;
    }

    @Override
    public String toString() {
        return "NewPurchaseNFRequest{" +
                "idPurchase=" + idPurchase +
                ", idBuyer=" + idBuyer +
                '}';
    }
}
