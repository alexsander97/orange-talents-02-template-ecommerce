package com.example.mercadolivre.newProduct;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String name;

    @ManyToOne
    private Product product;

    @Deprecated
    public Characteristic() {

    }

    public Characteristic(String name, String description, Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Characteristic that = (Characteristic) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(name, that.name) && Objects.equals(product, that.product);
    }


}
