package com.example.mercadolivre.newProduct;

import ch.qos.logback.core.util.COWArrayList;
import com.example.mercadolivre.newCategory.Category;
import com.example.mercadolivre.newUser.User;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<Characteristic> characteristics = new HashSet<>();

    private String description;

    @NotNull @Valid
    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ImageProduct> images = new HashSet<>();

    @Deprecated
    public Product() {

    }

    public Product(@NotBlank String name, @NotNull @Positive BigDecimal price,
                   @Positive Integer quantity, Collection<NewCharacteristicRequest> characteristics,
                   @NotBlank @Length(max = 1000) String description,
                   @NotNull @Valid Category category, @NotNull @Valid User user) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.dateCreation = LocalDateTime.now();
        this.user = user;
        this.characteristics.addAll(characteristics
                .stream()
                .map(characteristic -> characteristic.toEntity(this))
                .collect(Collectors.toSet()));

        Assert.isTrue(this.characteristics.size() >= 3, "Todo produto precisa ter 3 ou mais caracteristicas.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(quantity, product.quantity) && Objects.equals(characteristics, product.characteristics) && Objects.equals(description, product.description) && Objects.equals(category, product.category) && Objects.equals(user, product.user) && Objects.equals(dateCreation, product.dateCreation);
    }

    public void connectImages(Set<String> links) {
        Set<ImageProduct> images = links.stream().map(link -> new ImageProduct(this, link))
                .collect(Collectors.toSet());

        this.images.addAll(images);
    }

    public boolean belongsToUser(User user) {
        return this.user.equals(user);
    }
}
