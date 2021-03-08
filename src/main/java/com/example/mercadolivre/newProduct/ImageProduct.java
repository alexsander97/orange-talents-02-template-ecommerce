package com.example.mercadolivre.newProduct;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class ImageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Valid
    @NotNull
    private Product product;

    @URL
    @NotBlank
    @Column(name = "LINK", nullable = false)
    private String link;

    @Deprecated
    public ImageProduct() {

    }

    public ImageProduct(@NotNull @Valid Product product, @URL String link) {
        this.product = product;
        this.link = link;
    }

    public String getLink() {
        return link;
    }


}
