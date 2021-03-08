package com.example.mercadolivre.newPurchase;

import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newUser.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<>();

    @Deprecated
    public Purchase() {

    }

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

    public void addTransaction(ReturnGatewayPayment request) {
        Transaction newTransaction = request.toTransaction(this);
        Assert.isTrue(!this.transactions.contains(newTransaction),
                "Já existe uma transação igual a essa processada.");

        Assert.isTrue(transactionsSucessfullyCompleted().isEmpty(), "Essa compra já foi concluida com sucesso.");

        this.transactions.add(newTransaction);
    }

    private Set<Transaction> transactionsSucessfullyCompleted() {
        Set<Transaction> transactionsSucessfullyCompleted = this.transactions.stream()
                .filter(Transaction::successfullyCompleted)
                .collect(Collectors.toSet());

        Assert.isTrue(transactionsSucessfullyCompleted.size() <= 1, "Não pode ter mais de uma transação concluida com sucesso nessa compra." + this.id);
        return transactionsSucessfullyCompleted;
    }

    public boolean successfullyProcessed() {
        return !transactionsSucessfullyCompleted().isEmpty();
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", user=" + user +
                ", gatewayPayment=" + gatewayPayment +
                ", statusPayment=" + statusPayment +
                ", transactions=" + transactions +
                '}';
    }
}
