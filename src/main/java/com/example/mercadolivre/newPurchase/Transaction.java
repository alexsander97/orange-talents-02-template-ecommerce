package com.example.mercadolivre.newPurchase;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private StatusTransaction status;

    @NotBlank
    private String idTransactionGateway;

    @NotNull
    private LocalDateTime instant;

    @ManyToOne
    @NotNull @Valid
    private Purchase purchase;

    @Deprecated
    public Transaction() {

    }

    public Transaction(@NotNull StatusTransaction status, @NotBlank String idTransactionGateway,
                       @NotNull @Valid Purchase purchase) {
        this.status = status;
        this.idTransactionGateway = idTransactionGateway;
        this.instant = LocalDateTime.now();
        this.purchase = purchase;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean successfullyCompleted() {
        return this.status.equals(StatusTransaction.SUCESSO);
    }
}
