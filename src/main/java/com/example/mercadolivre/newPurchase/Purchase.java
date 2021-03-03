package com.example.mercadolivre.newPurchase;

import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newUser.User;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull @Valid
    private Product product;

    @Positive
    private Integer quantity;

    @ManyToOne
    @NotNull @Valid
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GatewayPayment gatewayPayment;

    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment;

    public Purchase(Product product, Integer quantity, User user, GatewayPayment gatewayPayment) {
        this.quantity = quantity;
        this.user = user;
        this.product = product;
        this.gatewayPayment = gatewayPayment;
        this.statusPayment = StatusPayment.INICIADO;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public GatewayPayment getGatewayPayment() {
        return gatewayPayment;
    }

    public StatusPayment getStatusPayment() {
        return statusPayment;
    }

    public User getUser() {
        return user;
    }
}
