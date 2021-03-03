package com.example.mercadolivre.newPurchase;

import com.example.mercadolivre.annotations.ExistsId;
import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.security.LoggedUser;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NewPurchaseRequest {

    @ExistsId(domainClass = Product.class, fieldName = "id")
    private Long idProduct;

    @NotNull
    @Positive
    private Integer quantity;

    private GatewayPayment gatewayPayment;

    public Purchase toEntity(EntityManager entityManager, LoggedUser loggedUser, Product product) {
        return new Purchase(product, quantity, loggedUser.get(), gatewayPayment);
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public GatewayPayment getGatewayPayment() {
        return gatewayPayment;
    }
}
